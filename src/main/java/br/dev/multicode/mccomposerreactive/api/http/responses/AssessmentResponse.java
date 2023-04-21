package br.dev.multicode.mccomposerreactive.api.http.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResponse {

  private String productId;
  private String userId;
  private Integer assessment;
  private String description;

}
