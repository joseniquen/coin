package com.example.monederomicroservice.aplication.model;

import com.example.monederomicroservice.domain.Coin;

public interface CoinKafka {
    public void create(Coin coin);
}
