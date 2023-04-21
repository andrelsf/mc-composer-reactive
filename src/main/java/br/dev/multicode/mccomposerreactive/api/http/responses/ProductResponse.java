package br.dev.multicode.mccomposerreactive.api.http.responses;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  private String name;
  private String description;
  private String category;
  private BigDecimal price;

}
