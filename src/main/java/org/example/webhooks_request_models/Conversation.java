package org.example.webhooks_request_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Conversation {
    private String id;
    @JsonProperty("expiration_timestamp")
    private String expirationTimestamp;
    private Origin origin;
}
