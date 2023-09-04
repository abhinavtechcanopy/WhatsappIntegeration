package org.example.request_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Setter
@NoArgsConstructor
public class CustomMessageBody implements MessageBody {

    @JsonProperty("messaging_product")
    private String messagingProduct;
    @JsonProperty("recipient_type")
    private String recipientType;
    private String to;
    private String type;
    private Text text;


}
