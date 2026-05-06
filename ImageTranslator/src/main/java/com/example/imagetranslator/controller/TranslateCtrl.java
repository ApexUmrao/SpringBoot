// ══════════════════════════════════════════════════════════
// FILE 1: application.properties  — add output directory
// ══════════════════════════════════════════════════════════
/*
# =====================================================
#  OpenAI
# =====================================================
spring.ai.openai.api-key=sk-YOUR_OPENAI_API_KEY_HERE
spring.ai.openai.chat.options.model=gpt-4o
spring.ai.openai.chat.options.max-tokens=4096
spring.ai.chat.client.enabled=true

# =====================================================
#  Translation
# =====================================================
app.target-language=Hindi

# =====================================================
#  ✅ Output directory — translated images saved here
#  Windows example: C:/Users/shivanshu.umrao/translated-images
#  Change this to any folder you want
# =====================================================
app.output.directory=C:/Users/shivanshu.umrao/translated-images

# =====================================================
#  Upload & Server
# =====================================================
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
server.port=8080
logging.level.com.example.imagetranslator=DEBUG
*/


// ══════════════════════════════════════════════════════════
// FILE 2: TranslateCtrl.java  — auto-save + auto-download
// ══════════════════════════════════════════════════════════

package com.example.imagetranslator.controller;

import com.example.imagetranslator.model.TextRegion;
import com.example.imagetranslator.service.EmbedService;
import com.example.imagetranslator.service.VisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TranslateCtrl {

    private final VisionService visionService;
    private final EmbedService  embedService;

    // ✅ Reads from application.properties
    @Value("${app.output.directory}")
    private String outputDirectory;

    private static final Set<String> ALLOWED = Set.of(
        "image/jpeg", "image/jpg", "image/png", "image/webp"
    );

    @PostMapping(value = "/translate", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> translate(
            @RequestPart("file") MultipartFile file,
            @RequestParam(required = false) String targetLang) {

        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().build();

            String ct = file.getContentType();
            if (ct == null || !ALLOWED.contains(ct.toLowerCase()))
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();

            log.info("Received: {} ({} bytes)", file.getOriginalFilename(), file.getSize());

            byte[] imgBytes = file.getBytes();

            // Step 1 — GPT-4o Vision
            List<TextRegion> regions = visionService.detectAndTranslate(imgBytes, ct);
            log.info("Regions detected: {}", regions.size());

            if (regions.isEmpty()) {
                log.info("No text found — returning original");
                return buildResponse(imgBytes, ct, file.getOriginalFilename());
            }

            // Step 2 — Decode image
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));
            if (img == null) {
                log.error("Could not decode image");
                return ResponseEntity.badRequest().build();
            }

            // Step 3 — Embed translations
            String fmt = resolveFmt(ct, file.getOriginalFilename());
            byte[] result = embedService.embed(img, regions, fmt);
            log.info("Result image: {} bytes", result.length);

            // ✅ Step 4 — Auto-save to output directory
            String savedPath = saveToDirectory(result, fmt, file.getOriginalFilename());

            // ✅ Step 5 — Return as downloadable file (browser auto-downloads)
            String outFilename = "translated_" + file.getOriginalFilename();
            return buildResponse(result, ct, outFilename);

        } catch (Exception e) {
            log.error("Pipeline failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Saves the translated image to the configured output directory.
     * Creates the directory if it doesn't exist.
     * Filename format: translated_YYYYMMDD_HHmmss_originalname.png
     */
    private String saveToDirectory(byte[] imageBytes, String format, String originalName) {
        try {
            // Create directory if it doesn't exist
            Path dir = Paths.get(outputDirectory);
            Files.createDirectories(dir);

            // Build filename with timestamp to avoid overwriting
            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "translated_" + timestamp + "_" + originalName;

            // Remove any path separators from original name for safety
            filename = filename.replaceAll("[/\\\\]", "_");

            Path outputPath = dir.resolve(filename);
            Files.write(outputPath, imageBytes);

            log.info("✅ Translated image saved to: {}", outputPath.toAbsolutePath());
            return outputPath.toAbsolutePath().toString();

        } catch (IOException e) {
            log.error("❌ Failed to save image to directory {}: {}",
                outputDirectory, e.getMessage());
            return null;
        }
    }

    /**
     * Builds HTTP response with Content-Disposition: attachment
     * so the browser automatically triggers a download.
     */
    private ResponseEntity<byte[]> buildResponse(byte[] data, String ct, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ct));
        // ✅ "attachment" triggers browser auto-download
        headers.setContentDisposition(
            ContentDisposition.attachment().filename(filename).build()
        );
        headers.setContentLength(data.length);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    private String resolveFmt(String ct, String fn) {
        if (ct != null) {
            if (ct.contains("jpeg") || ct.contains("jpg")) return "jpg";
            if (ct.contains("png"))  return "png";
            if (ct.contains("webp")) return "webp";
        }
        if (fn != null && fn.contains("."))
            return fn.substring(fn.lastIndexOf('.') + 1).toLowerCase();
        return "png";
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Image Translator is running!");
    }
}