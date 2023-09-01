package org.example.request_models;

import lombok.*;

@Data
@Setter

public class CustomMessageBody implements MessageBody {

    private String messaging_product;
    private String recipient_type;
    private String to;
    private String type;
    private Text text;


}
