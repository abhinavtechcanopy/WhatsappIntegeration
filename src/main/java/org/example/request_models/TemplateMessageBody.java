package org.example.request_models;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class TemplateMessageBody implements MessageBody {
    private String messaging_product;
    private String to;
    private String type;
    private Template Template;
}
