package org.example.webhooks_request_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.util.MessageStatus;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Status {
    private String id;
    @JsonProperty("recipient_id")
    private String recipientId;
    private MessageStatus status;
    private String timestamp;
    private Conversation conversation;
    private Pricing pricing;
}
