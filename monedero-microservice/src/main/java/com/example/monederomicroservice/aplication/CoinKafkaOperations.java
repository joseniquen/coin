package com.example.monederomicroservice.aplication;

import com.example.monederomicroservice.domain.Coin;

public interface CoinKafkaOperations {
    public void create(Coin coin);

}
