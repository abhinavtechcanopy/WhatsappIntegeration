package org.example.request_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@Setter
@NoArgsConstructor
public class Text {
    @JsonProperty("preview_url")
    private Boolean previewUrl;
    private String body;

}
