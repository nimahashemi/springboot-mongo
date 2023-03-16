package com.tutorials.springbootmongo.thirdparties;

import com.tutorials.springbootmongo.dto.Price;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class PriceService {

    WebClient webClient = WebClient.create("http://localhost:3000");

    public List<Price> getAllPrices() {
        List<Price> priceList = new ArrayList<>();

//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<Price>> response = restTemplate.exchange(
//                "http://localhost:3000/prices", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Price>>() {
//                });
//
//        List<Price> result = response.getBody();

        Flux<Price> prices = webClient.get()
                .uri("/prices")
                .retrieve()
                .bodyToFlux(Price.class);
        // prices.subscribe();
        prices.toStream().parallel().forEach(priceList::add);

        return priceList;
    }

    public Price getById(String id) {
        return webClient.get()
                .uri("/price/tutorial/" + id)
                .retrieve()
                .bodyToMono(Price.class)
                .block();
    }

    public Price savePrice(int amount, String tutorialId) {
        CustomePrice p = new CustomePrice(amount, tutorialId);
        return webClient.post()
                .uri("/price")
                .body(Mono.just(p), CustomePrice.class)
                .retrieve()
                .bodyToMono(Price.class)
                .block();
    }

    public Price updatePrice(int amount, String tutorialId) {
        CustomePrice p = new CustomePrice(amount, tutorialId);
        return webClient.patch()
                .uri("/price/tutorial/" + tutorialId)
                .body(Mono.just(p), CustomePrice.class)
                .retrieve()
                .bodyToMono(Price.class)
                .block();
    }

    public record CustomePrice(int price, String tutorialId) {}
}
