package com.example.monederomicroservice.infraestructure.modelDao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("monedero")
public class CoinDao {
    @Id
    public String telephone;
    public String idMonedero;
    public String mail;
    public String document;
    public String documentType;
    public String date;
    public boolean state;
}
