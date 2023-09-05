package org.example.dto.webhooksRequestRecieveDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Change {
   private Value value;
   private String field;
}
