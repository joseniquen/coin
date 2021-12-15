package com.example.monederomicroservice.aplication.impl;

import com.example.monederomicroservice.aplication.CoinKafkaOperations;
import com.example.monederomicroservice.aplication.CoinOperations;
import com.example.monederomicroservice.aplication.model.CoinRepository;
import com.example.monederomicroservice.domain.Coin;
import com.example.monederomicroservice.utils.EmailValidation;
import com.example.monederomicroservice.utils.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoinOperationsImpl implements CoinOperations {
    Logger logger = LoggerFactory.getLogger(CoinOperationsImpl.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Bogota"));

    private final CoinRepository coinRepository;
    public ResponseService responseService;
    public final CoinKafkaOperations kafkaOperations;

    @Override
    public Flux<Coin> list() {
        return coinRepository.list();
    }

    @Override
    public Mono<Coin> get(String monedero) {
        return coinRepository.get(monedero);
    }

    @Override
    public Mono<ResponseService> create(Coin monederoBootCoin) {
        return validateDataWalletToCreate(monederoBootCoin).flatMap(RS -> {
            responseService = RS;
            if (responseService.getStatus() == Status.OK) {
                return coinRepository.get(monederoBootCoin.getTelephone()).flatMap(walletR -> {
                    responseService.setStatus(Status.ERROR);
                    responseService.setMessage("El monedero " + monederoBootCoin.getTelephone() + " Ya existe!!");
                    responseService.setData(monederoBootCoin);
                    return Mono.just(responseService);
                }).switchIfEmpty(insertWallet(monederoBootCoin));
            } else {
                return Mono.just(responseService);
            }
        });
    }

    @Override
    public Mono<Coin> update(String wallet, Coin c) {
        return coinRepository.update(wallet, c);
    }

    @Override
    public void delete(String wallet) {
        coinRepository.delete(wallet);
    }

    public Mono<ResponseService> insertWallet(Coin monederoBootCoin) {
        responseService = new ResponseService();
        monederoBootCoin.setIdMonedero(monederoBootCoin.getTelephone());
        monederoBootCoin.setDate(dateTime.format(formatDate));
        monederoBootCoin.setState(true);
        return coinRepository.create(monederoBootCoin).flatMap(w -> {
            responseService.setStatus(Status.OK);
            responseService.setData(w);
            //Enviar monedero a Kafka para su registro en otros microservicios
            kafkaOperations.create(monederoBootCoin);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> validateDataWalletToCreate(Coin wallet) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        return Mono.just(wallet).flatMap(fm -> {
            if (!Optional.ofNullable(wallet.getDocument()).isPresent() || wallet.getDocument().length() < 8) {
                responseService.setMessage("Debe ingresar el documento y debe ser mayor a 8 caracteres");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(wallet.getDocumentType()).isPresent()) {
                responseService.setMessage("Debe ingresar el Tipo Documento");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(wallet.getTelephone()).isPresent() || wallet.getTelephone().length() < 9) {
                responseService.setMessage("Debe ingresar numero de telefono y debe ser mayor igual a 9 numeros");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(wallet.getMail()).isPresent()) {
                responseService.setMessage("Debe ingresar un correo");
                return Mono.just(responseService);
            }

            if (!EmailValidation.patternMatches(wallet.getMail())) {
                responseService.setMessage("El correo ingresado no es correcto!!");
                return Mono.just(responseService);
            }
            responseService.setStatus(Status.OK);
            responseService.setData(fm);
            return Mono.just(responseService);
        });
    }
}
