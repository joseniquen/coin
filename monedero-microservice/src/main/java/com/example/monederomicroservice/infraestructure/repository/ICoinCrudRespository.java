package com.example.monederomicroservice.infraestructure.repository;

import com.example.monederomicroservice.infraestructure.modelDao.CoinDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface ICoinCrudRespository extends ReactiveCrudRepository<CoinDao, String> {

}
