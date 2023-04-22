package br.dev.multicode.mccomposerreactive.configs.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CircuitBreakerConfiguratotion {

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
        .timeLimiterConfig(TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(3))
            .build())
        .build());
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> assessmentsClientCusomtizer() {
    return factory -> {
      factory.configure(builder -> builder
          .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
          .timeLimiterConfig(TimeLimiterConfig.custom()
              .timeoutDuration(Duration.ofSeconds(2))
              .build()),
          "assessments-client");
    };
  }

  @Bean
  public RegistryEventConsumer<CircuitBreaker> circuitBreakerLogger()
  {
    return new RegistryEventConsumer<CircuitBreaker>() {
      @Override
      public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
        entryAddedEvent.getAddedEntry()
            .getEventPublisher()
            .onStateTransition(event -> log.info(event.toString()));
      }

      @Override
      public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
        entryRemoveEvent.getRemovedEntry()
            .getEventPublisher()
            .onStateTransition(event -> log.info(event.toString()));
      }

      @Override
      public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
        entryReplacedEvent.getNewEntry()
            .getEventPublisher()
            .onStateTransition(event -> log.info(event.toString()));
      }
    };
  }

}
