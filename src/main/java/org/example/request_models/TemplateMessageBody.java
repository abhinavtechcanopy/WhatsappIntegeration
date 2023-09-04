package org.example.request_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@Data
@Setter
@NoArgsConstructor
public class TemplateMessageBody implements MessageBody {
    @JsonProperty("messaging_product")
    private String messagingProduct;
    private String to;
    private String type;
    private Template Template;
}
