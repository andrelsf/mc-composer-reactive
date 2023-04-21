package br.dev.multicode.mccomposerreactive.api.http.clients;

import br.dev.multicode.mccomposerreactive.api.http.responses.ProductResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MCProductsClient {

  private static final String URI_MC_PRODUCTS;

  static {
    URI_MC_PRODUCTS = "/api/products/{productId}";
  }

  private final WebClient apiMcProducts;

  public Mono<ProductResponse> getProductById(final String productId) {
    return apiMcProducts.get()
      .uri(URI_MC_PRODUCTS, productId)
      .retrieve()
      .bodyToMono(ProductResponse.class)
      .doOnError(ex -> log.error("Fail to call ms products. ERROR: {}", ex.getMessage()))
      .doOnSuccess(response -> log.info("Call ms products success, productId={}", productId));
  }
}
