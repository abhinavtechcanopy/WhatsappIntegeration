package org.example.dto.WhatsappMessageResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contacts {
    private String input;
    @JsonProperty("wa_id")
    private String whatsappId;
}
