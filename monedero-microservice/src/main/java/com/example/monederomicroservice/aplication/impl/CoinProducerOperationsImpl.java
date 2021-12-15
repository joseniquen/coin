package com.example.monederomicroservice.aplication.impl;

import com.example.monederomicroservice.aplication.CoinKafkaOperations;
import com.example.monederomicroservice.aplication.model.CoinKafka;
import com.example.monederomicroservice.domain.Coin;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinProducerOperationsImpl implements CoinKafkaOperations {
    Logger logger = LoggerFactory.getLogger(CoinProducerOperationsImpl.class);
    private final CoinKafka coinKafka;

    @Override
    public void create(Coin coin) {
        coinKafka.create(coin);
    }
}
