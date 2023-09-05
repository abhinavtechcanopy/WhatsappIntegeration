package org.example.dto.WhatsappMessageResponseDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class WhatsappMessageResponseDto {

    @JsonProperty("messaging_product")
   private String messagingProduct;
    private List<Contacts> contacts;
    private List<ResponseMessage> messages;

}