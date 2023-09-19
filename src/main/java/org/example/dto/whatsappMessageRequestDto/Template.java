package org.example.dto.whatsappMessageRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class Template {
  private String name;
  private Language language;
}
