package org.example.webhooks_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WhatsappBusinessAccountValue {
    @JsonProperty("messaging_product")
    private String messagingProduct;
    private WhatsappBusinessAccountMetadata metadata;
    private List<WhatsappBusinessAccountStatus> statuses;
}
