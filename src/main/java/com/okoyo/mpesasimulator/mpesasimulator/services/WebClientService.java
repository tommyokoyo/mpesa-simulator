package com.okoyo.mpesasimulator.mpesasimulator.services;

import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressCallbackDTO;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressCallbackResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Objects;

@Service
public class WebClientService {
    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(WebClientService.class);

    @Autowired
    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient getWebClient(String url) {
        return webClientBuilder.baseUrl(url).build();
    }

    @Retry(name = "MpesaExpressCallback", fallbackMethod = "mpesaExpressRequestFallback")
    @CircuitBreaker(name = "mpesaExpressRequestCircuitBreaker", fallbackMethod = "mpesaExpressRequestFallback")
    public Mono<MpesaExpressCallbackResponse> mpesaExpressCallback(MpesaExpressCallbackDTO mpesaExpressCallbackDTO, String url) {
        WebClient webClient = getWebClient(url);
        return webClient.post()
                .bodyValue(mpesaExpressCallbackDTO)
                .retrieve()
                .bodyToMono(MpesaExpressCallbackResponse.class)
                .onErrorResume(ConnectException.class, ex -> Mono.just(new MpesaExpressCallbackResponse("1", "Connection error: " + ex.getMessage())))
                .onErrorResume(WebClientResponseException.class, this::handleWebClientException);
    }

    private Mono<MpesaExpressCallbackResponse> handleWebClientException(WebClientResponseException ex) {
        MpesaExpressCallbackResponse mpesaExpressCallbackResponse = ex.getResponseBodyAs(MpesaExpressCallbackResponse.class);
        return Mono.just(Objects.requireNonNullElseGet(mpesaExpressCallbackResponse, () -> new MpesaExpressCallbackResponse("1", "Client not available")));
    }

    public Mono<String> mpesaExpressRequestFallback(Throwable throwable) {
        if (throwable instanceof ConnectException) {
            logger.error("Connection error: {}", throwable.getMessage());
            return Mono.just("Client Endpoint not available");
        } else if (throwable instanceof WebClientResponseException) {
            logger.error("Client error: {}", throwable.getMessage());
            return Mono.just("Client error");
        }
        return Mono.just("Client is not available");
    }
}
