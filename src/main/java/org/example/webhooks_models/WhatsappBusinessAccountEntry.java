package org.example.webhooks_models;

import lombok.Data;

import java.util.List;

@Data
public class WhatsappBusinessAccountEntry {
   private String id;
    private List<WhatsappBusinessAccountChange> changes;


}

