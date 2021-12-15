package com.example.monederomicroservice.infraestructure.repository;

import com.example.monederomicroservice.aplication.model.CoinRepository;
import com.example.monederomicroservice.domain.Coin;
import com.example.monederomicroservice.infraestructure.modelDao.CoinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CoinCrudRepository implements CoinRepository {
    @Autowired
    ICoinCrudRespository crudRepository;

    @Override
    public Mono<Coin> get(String debitcardaccount) {
        return crudRepository.findById(debitcardaccount).map(this::PersonDaoToPerson);
    }

    @Override
    public Flux<Coin> list() {
        return crudRepository.findAll().map(this::PersonDaoToPerson);
    }

    @Override
    public Mono<Coin> create(Coin debitcardaccount) {
        return crudRepository.save(PersonToPersonDao(debitcardaccount)).map(this::PersonDaoToPerson);
    }

    @Override
    public Mono<Coin> update(String debitcardaccount, Coin c) {
        return crudRepository.save(PersonToPersonDao(c)).map(this::PersonDaoToPerson);
    }

    @Override
    public void delete(String debitcardaccount) {
        crudRepository.deleteById(debitcardaccount).subscribe();
    }

    public Coin  PersonDaoToPerson(CoinDao md) {
        Coin m = new Coin();
        m.setIdMonedero(md.getIdMonedero());
        m.setDocument(md.getDocument());
        m.setDocumentType(md.getDocumentType());
        m.setTelephone(md.getTelephone());
        m.setMail(md.getMail());
        m.setDate(md.getDate());
        m.setState(md.isState());
        return m;
    }

    public CoinDao PersonToPersonDao(Coin p) {
        CoinDao pd = new CoinDao();
        pd.setDate(p.getIdMonedero());
        pd.setDocument(p.getDocument());
        pd.setDocumentType(p.getDocumentType());
        pd.setTelephone(p.getTelephone());
        pd.setMail(p.getMail());
        pd.setDate(p.getDate());
        pd.setState(p.isState());
        return pd;
    }

}
