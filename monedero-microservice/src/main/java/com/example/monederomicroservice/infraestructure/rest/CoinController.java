package com.example.monederomicroservice.infraestructure.rest;

import com.example.monederomicroservice.aplication.CoinOperations;
import com.example.monederomicroservice.domain.Coin;
import com.example.monederomicroservice.utils.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/monedero")
@RequiredArgsConstructor
public class CoinController {
    Logger logger = LoggerFactory.getLogger(CoinController.class);
    private final CoinOperations operations;

    @GetMapping
    public Flux<Coin> listAll() {
        return operations.list();
    }

    @GetMapping("/{id}")
    public Mono<Coin> get(@PathVariable("id") String id) {
        return operations.get(id);
    }

    @PostMapping
    public Mono<ResponseEntity> create(@RequestBody Coin monederoBootCoin) {
        return operations.create(monederoBootCoin).flatMap(pR -> {
            if (pR.getStatus() == Status.OK) {
                return Mono.just(new ResponseEntity(pR.getData(), HttpStatus.OK));
            } else {
                return Mono.just(new ResponseEntity(pR.getMessage(), HttpStatus.BAD_REQUEST));
            }
        });
    }

    @PutMapping("/{id}")
    public Mono<Coin> update(@PathVariable("id") String id, @RequestBody Coin monederoBootCoin) {
        return operations.update(id, monederoBootCoin);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        operations.delete(id);
    }
}
