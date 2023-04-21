package br.dev.multicode.mccomposerreactive.api.http.responses;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsResponse {

  private String name;
  private String description;
  private String category;
  private BigDecimal price;
  private List<AssessmentResponse> assessmentResponses;

  public static ProductDetailsResponse of(ProductResponse productResponse, List<AssessmentResponse> assessmentResponses) {
    return ProductDetailsResponse.builder()
      .name(productResponse.getName())
      .description(productResponse.getDescription())
      .category(productResponse.getCategory())
      .price(productResponse.getPrice())
      .assessmentResponses(assessmentResponses)
      .build();
  }
}
