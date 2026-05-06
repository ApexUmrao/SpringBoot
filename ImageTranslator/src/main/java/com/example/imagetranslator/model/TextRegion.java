package com.example.imagetranslator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Maps one entry from the JSON array returned by GPT-4o.
 * Coordinates are percentages (0-100) of image width/height.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextRegion {

    @JsonProperty("original_text")
    private String originalText;

    @JsonProperty("translated_text")
    private String translatedText;

    @JsonProperty("x_percent")
    private double xPct;

    @JsonProperty("y_percent")
    private double yPct;

    @JsonProperty("width_percent")
    private double wPct;

    @JsonProperty("height_percent")
    private double hPct;
}