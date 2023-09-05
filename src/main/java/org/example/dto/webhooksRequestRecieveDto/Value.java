package org.example.dto.webhooksRequestRecieveDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Value {
    @JsonProperty("messaging_product")
    private String messagingProduct;
    private Metadata metadata;
    private List<Status> statuses;
}
