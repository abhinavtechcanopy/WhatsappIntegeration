package org.example.dto.whatsappMessageRequestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@Data
@Setter
@NoArgsConstructor
public class WhatsappTemplateMessage {
    @JsonProperty("messaging_product")
    private String messagingProduct;
    private String to;
    private String type;
    private Template Template;
}
