package org.example.dto.webhooksRequestRecieveDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Pricing {
    @JsonProperty("pricing_model")
    private String pricingModel;
    private boolean billable;
    private String category;
}
