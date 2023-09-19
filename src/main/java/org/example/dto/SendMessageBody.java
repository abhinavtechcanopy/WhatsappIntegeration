package org.example.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
public class SendMessageBody {

    public enum LanguageCode{
        en_Us
    }
    public enum MessageType{
        text,template
    }
    @NotBlank(message = "messaging Product is required")
    String messagingProduct;
    @NotBlank(message = "recipient Type is required")
    String recipientType;
    @Size(min = 12, max = 12, message = "check phone number do add country code")
//    @Pattern(regexp = "[0-9]+", message = "invalid phone number")
    @NotBlank(message = "Phone Number is required")
    String toPhoneNumber;
    @NotBlank(message = "message Type is required")
    String messageType;
    @NotBlank(message = "message is required")
//    @Pattern(regexp = "text|template", message = "Invalid message type")
    String message;
    @NotBlank(message = "language Code is required" )
    @Pattern(regexp = "en_US",message = "Invalid languageCode")
    String languageCode;

    public void setLanguageCode(String languageCode) {
        if (!languageCode.matches("en_US")) {
            throw new IllegalArgumentException("Invalid languageCode");
        }
        this.languageCode = languageCode;
    }
}