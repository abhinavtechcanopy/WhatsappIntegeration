package org.example.webhooks_models;

import lombok.Data;

@Data
public class WebhookResponseStatus {

    private String id;
    private String recipientId;
    private String status;
    private String timestamp;
    private WhatsappBusinessAccountConversation conversation;
    private WhatsappBusinessAccountPricing pricing;

}