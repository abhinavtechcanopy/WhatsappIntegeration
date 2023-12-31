package org.example.dto.whatsappMessageRequestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class Text {
    @JsonProperty("preview_url")
    private Boolean previewUrl;
    private String body;

}
