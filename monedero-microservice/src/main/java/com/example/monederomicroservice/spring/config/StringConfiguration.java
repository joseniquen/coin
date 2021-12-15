package com.example.monederomicroservice.spring.config;


import com.example.monederomicroservice.aplication.model.CoinRepository;
import com.example.monederomicroservice.infraestructure.repository.CoinCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringConfiguration {
    @Bean
    public CoinRepository coinRepository() {
        return new CoinCrudRepository();
    }
}
