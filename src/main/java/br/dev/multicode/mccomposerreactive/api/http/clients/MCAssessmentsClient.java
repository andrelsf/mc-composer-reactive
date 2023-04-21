package br.dev.multicode.mccomposerreactive.api.http.clients;

import br.dev.multicode.mccomposerreactive.api.http.responses.AssessmentResponse;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MCAssessmentsClient {

  private static final String ERROR_MESSAGE;
  private static final String URI_MC_ASSESSMENTS_PRODUCTS;

  static {
    URI_MC_ASSESSMENTS_PRODUCTS = "/api/assessments/products/{productId}";
    ERROR_MESSAGE = "Fail to call ms assessments. ERROR: {}";
  }

  private final WebClient apiMcAssessments;

  public Mono<List<AssessmentResponse>> getAssessmentsByProductIdFlux(@NonNull final String productId) {
    return apiMcAssessments.get()
        .uri(URI_MC_ASSESSMENTS_PRODUCTS, productId)
        .retrieve()
        .bodyToFlux(AssessmentResponse.class)
        .doOnError(ex -> log.error(ERROR_MESSAGE, ex.getMessage()))
        .collectList()
        .log();
  }

  private Mono<List<AssessmentResponse>> getAssessmentsByProductIdFallback(Throwable e) {
    log.warn("Method fallback called. {}", e.getMessage());
    return Mono.just(new ArrayList<>());
  }
}
