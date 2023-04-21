package br.dev.multicode.mccomposerreactive.api.http.resources;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import br.dev.multicode.mccomposerreactive.services.ComposeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class ComposerResource {

  @Bean
  RouterFunction<ServerResponse> routes(final ComposeHandler handler) {
    return RouterFunctions.route()
        .path("/compose",
            builder -> builder.nest(RequestPredicates.accept(APPLICATION_JSON),
                nestedBuilder -> nestedBuilder.GET("/products/{productId}/details", handler::getProductWithAssessmentsById)))
        .build();
  }

}
