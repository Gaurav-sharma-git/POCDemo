package com.user.service.config;



import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.ConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

@Configuration
public class CircuitBreakConfig {

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(CircuitBreakerRegistry circuitBreakerRegistry) {
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .minimumNumberOfCalls(5)
//                .automaticTransitionFromOpenToHalfOpenEnabled(true)
//                .slidingWindowSize(10)
//                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
//                .waitDurationInOpenState(Duration.ofMillis(6000))
//                .permittedNumberOfCallsInHalfOpenState(3)
//                .build();
//        circuitBreakerRegistry.circuitBreaker("TodoServiceBreaker", circuitBreakerConfig);
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(circuitBreakerConfig)
//                .build());
//    }







//@Autowired
//CircuitBreakerRegistry circuitBreakerRegistry;
//
//    @Bean
//    public Optional<CircuitBreaker> defaultCircuitBreaker() {
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .minimumNumberOfCalls(5)
//                .automaticTransitionFromOpenToHalfOpenEnabled(true)
//                .slidingWindowSize(5)
//                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
//                .waitDurationInOpenState(Duration.ofMillis(6000))
//                .permittedNumberOfCallsInHalfOpenState(3)
//                .recordExceptions(IOException.class, TimeoutException.class, DataIntegrityViolationException.class)
//                .build();
////        // Create a CircuitBreakerRegistry with a custom global configuration
////
//
//        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);
//        CircuitBreaker circuitBreaker = registry.circuitBreaker("TodoServiceBreaker");
//        return circuitBreakerRegistry.replace("TodoServiceBreaker", circuitBreaker);
//    }


//@Configuration
//public class CircuitBreakConfig {
//
// //@Autowired
//
//    @Bean
//    public CircuitBreaker defaultCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//                .failureRateThreshold(50)
//                .minimumNumberOfCalls(5)
//                .automaticTransitionFromOpenToHalfOpenEnabled(true)
//                .slidingWindowSize(10)
//                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
//                .waitDurationInOpenState(Duration.ofMillis(6000))
//                .permittedNumberOfCallsInHalfOpenState(3)
//                .recordExceptions(IOException.class, TimeoutException.class, DataIntegrityViolationException.class)
//                .build();
//        System.out.println("Circuit Breaker Configuration: {}"+ circuitBreakerConfig.getFailureRateThreshold());
//// // Create a CircuitBreakerRegistry with a custom global configuration
//// CircuitBreakerRegistry circuitBreakerRegistry =
//// CircuitBreakerRegistry.of(circuitBreakerConfig);
//        return circuitBreakerRegistry.circuitBreaker("TodoServiceBreaker",circuitBreakerConfig);
//    }
//
//}

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//                .build();
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .timeLimiterConfig(
//                        TimeLimiterConfig.custom()
//                                .timeoutDuration(Duration.ofMillis(10000))
//                                .build())
//                .circuitBreakerConfig(circuitBreakerConfig)
//                .build());
//    }








//
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(CircuitBreakerRegistry circuitBreakerRegistry,TimeLimiterRegistry timeLimiterRegistry) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .minimumNumberOfCalls(5)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .slidingWindowSize(10)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .waitDurationInOpenState(Duration.ofMillis(6000))
                .permittedNumberOfCallsInHalfOpenState(3)
                .recordExceptions(TimeoutException.class)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(2000))
                .build();
        circuitBreakerRegistry.circuitBreaker("TodoServiceBreaker", circuitBreakerConfig);
        timeLimiterRegistry.timeLimiter("myTimeLimiter", timeLimiterConfig);
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }


//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> defaultTimeCustomizer(TimeLimiterRegistry timeLimiterRegistry) {
//        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofMillis(10000))
//                .build();
//        //CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//        //      .build();
//        timeLimiterRegistry.timeLimiter("myTimeLimiter", timeLimiterConfig);
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .timeLimiterConfig(timeLimiterConfig)
//                .build());
//    }


//    @Bean
//    public CircuitBreakerRegistry circuitBreakerRegistry() {
//        return CircuitBreakerRegistry.ofDefaults();
//    }
//
//    @Bean
//    public CircuitBreaker customCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
//        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//                .build();
//
//        return circuitBreakerRegistry.circuitBreaker("TodoServiceBreaker", circuitBreakerConfig);
//    }


//    @Bean
//    public TimeLimiterConfig timeLimiterConfig() {
//        return TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofMillis(1000)) // Set your maximum execution time
//                .cancelRunningFuture(true) // Optionally cancel the running future upon timeout
//                .build();
//    }

//    @Bean
//    public Customizer<Resilience4JCircuitBreakerFactory> timeLimiter() {
//         TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofMillis(10000)) // Configure your timeout here
//                // Add other TimeLimiter configuration options if needed
//                .build();
//
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .timeLimiterConfig(timeLimiterConfig)
//                .build());
//    }


//    @Bean
//    public Customizer<Resilience4JTimeLimiterFactory> timeLimiterCustomizer(TimeLimiterRegistry timeLimiterRegistry) {
//        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofMillis(1000)) // Configure your timeout here
//                // Add other TimeLimiter configuration options if needed
//                .build();
//
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .timeLimiterConfig(timeLimiterConfig)
//                .build());
//    }

//    @Bean
//    public TimeLimiter timeLimiter() {
//        TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.ofDefaults();
//
//        return TimeLimiter.of("myTimeLimiter", timeLimiterRegistry, TimeLimiterConfig.custom()
//                .timeoutDuration(Duration.ofMillis(1000)) // Configure your timeout here
//                .build());
//    }

}