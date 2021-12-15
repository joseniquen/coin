package com.example.monederomicroservice.domain;

import lombok.Data;

@Data
public class Coin {
    public String idMonedero;
    public String telephone;
    public String mail;
    public String document;
    public String documentType;
    public String date;
    public boolean state;
}
