package br.dev.multicode.mccomposerreactive.services;

import br.dev.multicode.mccomposerreactive.api.http.clients.MCAssessmentsClient;
import br.dev.multicode.mccomposerreactive.api.http.clients.MCProductsClient;
import br.dev.multicode.mccomposerreactive.api.http.responses.ProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ComposeHandler {

  private final MCProductsClient productsClient;
  private final MCAssessmentsClient assessmentsClient;

  public Mono<ServerResponse> getProductWithAssessmentsById(final ServerRequest request) {
    final String productId = request.pathVariable("productId");
    return Mono.zip(
            productsClient.getProductById(productId),
            assessmentsClient.getAssessmentsByProductIdFlux(productId),
            ProductDetailsResponse::of)
        .flatMap(response ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(response)))
        .switchIfEmpty(ServerResponse.notFound().build())
        .log();
  }
}
