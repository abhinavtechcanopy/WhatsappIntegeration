package org.example.dto.whatsappMessageRequestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class WhatsappTextMessage {

    @JsonProperty("messaging_product")
    private String messagingProduct;
    @JsonProperty("recipient_type")
    private String recipientType;
    private String to;
    private String type;
    private Text text;


}
