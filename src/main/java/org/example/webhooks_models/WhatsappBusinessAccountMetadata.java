package org.example.webhooks_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WhatsappBusinessAccountMetadata {
    @JsonProperty("display_phone_number")
    private String displayPhoneNumber;
    @JsonProperty("phone_number_id")
    private String phoneNumberId;
}
