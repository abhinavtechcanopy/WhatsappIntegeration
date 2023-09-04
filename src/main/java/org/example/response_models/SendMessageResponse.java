package org.example.response_models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class SendMessageResponse {

    @JsonProperty("messaging_product")
   private String messagingProduct;
    private List<Contacts> contacts;
    private List<ResponseMessage> messages;

}