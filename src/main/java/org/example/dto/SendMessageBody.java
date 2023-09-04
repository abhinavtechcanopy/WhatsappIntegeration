package org.example.dto;


import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Data
public class SendMessageBody {

    @NotBlank(message = "messaging Product is required")
    String messagingProduct;
    @NotBlank(message = "recipient Type is required")
    String recipientType;
    @Size(min = 12, max = 12, message = "check phone number do add country code")
    @Pattern(regexp = "[0-9]+", message = "invalid phone number")
    @NotBlank(message = "Phone Number is required")
    String toPhoneNumber;
    @NotBlank(message = "message Type is required")
    String messageType;
    @NotBlank(message = "message is required")
    String message;
    @NotBlank(message = "language Code is required")
    String languageCode;


}