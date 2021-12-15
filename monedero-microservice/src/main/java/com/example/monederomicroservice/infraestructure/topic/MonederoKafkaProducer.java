package com.example.monederomicroservice.infraestructure.topic;

import com.example.monederomicroservice.aplication.model.CoinKafka;
import com.example.monederomicroservice.domain.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class MonederoKafkaProducer implements CoinKafka {

    @Autowired
    private KafkaTemplate<String, Coin> kafkaTemplate;
    private final String TOPIC_MONEDERO = "topic-Monedero";

    @Override
    public void create(Coin monederoBootCoin) {
        this.kafkaTemplate.send(TOPIC_MONEDERO, monederoBootCoin);

        ListenableFuture<SendResult<String, Coin>> future = this.kafkaTemplate.send(TOPIC_MONEDERO, monederoBootCoin);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Coin>>() {

            @Override
            public void onSuccess(SendResult<String, Coin> result) {
                System.out.println("Monedero=[" + monederoBootCoin
                        + "] con offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Imposible enviar Monedero message=["
                        + monederoBootCoin + "] due to : " + ex.getMessage());
            }
        });
    }
}
