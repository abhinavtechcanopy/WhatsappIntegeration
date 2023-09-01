package org.example.webhooks_models;

import lombok.Data;

@Data
public class WhatsappBusinessAccountChange {
    WhatsappBusinessAccountValue value;
    String field;

}
