package org.example.response_models;


import lombok.Data;

import java.util.List;

@Data
public class WhatsappMessageResponse {

    String messaging_product;
    List<Contacts> contacts;
    List<ResponseMessage> messages;

}