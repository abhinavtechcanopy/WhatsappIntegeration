package org.example.webhooks_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WhatsappBusinessAccountStatus {
    private String id;
    private String status;
    private long timestamp;
    @JsonProperty("recipient_id")
    private String recipientId;
    private WhatsappBusinessAccountConversation conversation;
    private WhatsappBusinessAccountPricing pricing;



}
