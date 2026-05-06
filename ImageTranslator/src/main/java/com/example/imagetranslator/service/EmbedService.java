package com.example.imagetranslator.service;

import com.example.imagetranslator.model.TextRegion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class EmbedService {

    /**
     * Fonts that support Devanagari (Hindi) and other non-Latin scripts.
     * We try each one in order and pick the first one available on the system.
     */
    private static final List<String> PREFERRED_FONTS = Arrays.asList(
        // ── Linux / Docker (Noto fonts installed in Dockerfile) ──
        "Noto Sans Devanagari",  // ✅ Best for Hindi in Docker/Linux
        "Noto Sans",             // ✅ Wide Unicode — Linux
        "Lohit Devanagari",      // ✅ Hindi fallback — Linux
        // ── Windows ──────────────────────────────────────────────
        "Nirmala UI",            // ✅ Windows 8+ — best Hindi
        "Mangal",                // ✅ Windows XP+ — Hindi
        "Arial Unicode MS",      // ✅ Windows + Office
        "Segoe UI",              // ✅ Windows partial Unicode
        // ── Generic fallbacks ─────────────────────────────────────
        "Dialog",
        "SansSerif"
    );

    public byte[] embed(BufferedImage original,
                        List<TextRegion> regions,
                        String format) throws IOException {

        BufferedImage canvas = deepCopy(original);
        Graphics2D g = canvas.createGraphics();
        applyHints(g);

        int W = canvas.getWidth();
        int H = canvas.getHeight();

        // ✅ Find the best available font for Unicode/Hindi rendering
        String bestFont = findBestFont(regions);
        log.info("Using font: {}", bestFont);

        for (TextRegion r : regions) {
            String text = r.getTranslatedText();
            if (text == null || text.isBlank()) continue;

            int x = clamp(pct(r.getXPct(), W), 0, W - 1);
            int y = clamp(pct(r.getYPct(), H), 0, H - 1);
            int w = clamp(pct(r.getWPct(), W), 10, W - x);
            int h = clamp(pct(r.getHPct(), H), 10, H - y);

            // 1. Sample background color
            Color bg = sampleColor(original, x, y, w, h);

            // 2. Erase original text with a slightly padded fill
            g.setColor(bg);
            g.fillRect(x - 3, y - 3, w + 6, h + 6);

            // 3. Pick contrasting text color
            g.setColor(contrastColor(bg));

            // 4. Fit font — use bestFont that supports the script
            Font font = fitFont(g, text, w, h, bestFont);
            g.setFont(font);

            // 5. Draw wrapped text
            drawWrapped(g, text, x, y, w, h);
        }

        g.dispose();
        return toBytes(canvas, format);
    }

    /**
     * Finds the first font on this system that can display
     * the translated text (including Hindi/Devanagari characters).
     */
    private String findBestFont(List<TextRegion> regions) {
        // Collect a sample of translated text to test against
        String sample = regions.stream()
            .map(TextRegion::getTranslatedText)
            .filter(t -> t != null && !t.isBlank())
            .findFirst()
            .orElse("A");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        List<String> available = Arrays.asList(ge.getAvailableFontFamilyNames());
        log.debug("Available fonts (first 10): {}",
            available.subList(0, Math.min(10, available.size())));

        for (String fontName : PREFERRED_FONTS) {
            if (available.contains(fontName)) {
                Font testFont = new Font(fontName, Font.PLAIN, 14);
                // Check if this font can display the sample text
                if (testFont.canDisplayUpTo(sample) == -1) {
                    log.info("Font '{}' fully supports the translated text", fontName);
                    return fontName;
                } else {
                    log.debug("Font '{}' cannot fully display: {}", fontName, sample);
                }
            }
        }

        // Fallback: find any system font that can display the text
        for (String fontName : available) {
            Font f = new Font(fontName, Font.PLAIN, 14);
            if (f.canDisplayUpTo(sample) == -1) {
                log.info("Fallback font found: '{}'", fontName);
                return fontName;
            }
        }

        log.warn("No font found that fully supports the text. Using Dialog.");
        return "Dialog";
    }

    private void drawWrapped(Graphics2D g, String text,
                              int x, int y, int w, int h) {
        FontMetrics fm = g.getFontMetrics();
        int lh = fm.getHeight();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        int cy = y + fm.getAscent();

        for (String word : words) {
            String test = line.isEmpty() ? word : line + " " + word;
            if (fm.stringWidth(test) <= w) {
                line = new StringBuilder(test);
            } else {
                if (cy <= y + h) {
                    g.drawString(line.toString(), x, cy);
                    cy += lh;
                }
                line = new StringBuilder(word);
            }
        }
        if (!line.isEmpty() && cy <= y + h + fm.getAscent()) {
            g.drawString(line.toString(), x, cy);
        }
    }

    private Font fitFont(Graphics2D g, String text, int w, int h, String fontName) {
        float sz = Math.max(h * 0.72f, 9f);
        Font font = new Font(fontName, Font.BOLD, (int) sz);
        String longest = "";
        for (String p : text.split("\\s+"))
            if (p.length() > longest.length()) longest = p;
        while (sz > 7) {
            FontMetrics fm = g.getFontMetrics(font);
            if (fm.stringWidth(longest) <= w && fm.getHeight() <= h) break;
            sz -= 0.5f;
            font = font.deriveFont(sz);
        }
        return font;
    }

    private Color sampleColor(BufferedImage img, int x, int y, int w, int h) {
        int step = Math.max(1, Math.min(w, h) / 6);
        long r = 0, gv = 0, b = 0, cnt = 0;
        for (int px = x; px < x + w && px < img.getWidth(); px += step)
            for (int py = y; py < y + h && py < img.getHeight(); py += step) {
                Color c = new Color(img.getRGB(px, py));
                r += c.getRed(); gv += c.getGreen();
                b += c.getBlue(); cnt++;
            }
        return cnt == 0 ? Color.WHITE
            : new Color((int)(r/cnt), (int)(gv/cnt), (int)(b/cnt));
    }

    private Color contrastColor(Color bg) {
        double lum = (0.299*bg.getRed() + 0.587*bg.getGreen() + 0.114*bg.getBlue()) / 255.0;
        return lum > 0.5 ? Color.BLACK : Color.WHITE;
    }

    private void applyHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    private BufferedImage deepCopy(BufferedImage src) {
        int type = src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType();
        BufferedImage c = new BufferedImage(src.getWidth(), src.getHeight(), type);
        Graphics2D g = c.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return c;
    }

    private byte[] toBytes(BufferedImage img, String fmt) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if ("jpg".equalsIgnoreCase(fmt) || "jpeg".equalsIgnoreCase(fmt)) {
            BufferedImage rgb = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = rgb.createGraphics();
            g.drawImage(img, 0, 0, Color.WHITE, null);
            g.dispose();
            ImageIO.write(rgb, "jpg", out);
        } else {
            ImageIO.write(img, fmt.isEmpty() ? "png" : fmt, out);
        }
        return out.toByteArray();
    }

    private int pct(double p, int total) { return (int) Math.round(p / 100.0 * total); }
    private int clamp(int v, int min, int max) { return Math.max(min, Math.min(v, max)); }
}