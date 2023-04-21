package br.dev.multicode.mccomposerreactive.configs.webclient;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  private final String urlMcProducts;
  private final String urlMcAssessments;

  public WebClientConfig(
      @Value("${multicode.apis.urlMcProducts}") String urlMcProducts,
      @Value("${multicode.apis.urlMcAssessments}") String urlMcAssessments)
  {
    this.urlMcProducts = urlMcProducts;
    this.urlMcAssessments = urlMcAssessments;
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public WebClient.Builder webClientBuilder(ObjectProvider<WebClientCustomizer> customizerProvider) {
    WebClient.Builder webClientBuilder = WebClient.builder();
    customizerProvider.orderedStream()
        .forEach(customizer -> customizer.customize(webClientBuilder));

    return webClientBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
  }

  @Bean
  public WebClient apiMcProducts(ObjectProvider<WebClientCustomizer> customizerProvider) {
    return webClientBuilder(customizerProvider)
        .baseUrl(urlMcProducts)
        .build();
  }

  @Bean
  public WebClient apiMcAssessments(ObjectProvider<WebClientCustomizer> customizerProvider) {
    return webClientBuilder(customizerProvider)
        .baseUrl(urlMcAssessments)
        .build();
  }
}
