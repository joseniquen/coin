package com.example.monederomicroservice.aplication;


import com.example.monederomicroservice.aplication.impl.ResponseService;
import com.example.monederomicroservice.domain.Coin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoinOperations {
    public Flux<Coin> list();

    public Mono<Coin> get(String person);

    public Mono<ResponseService> create(Coin person);

    public Mono<Coin> update(String id, Coin person);

    public void delete(String id);
}
