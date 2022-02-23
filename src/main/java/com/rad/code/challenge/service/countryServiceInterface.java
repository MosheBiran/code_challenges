package com.rad.code.challenge.service;

import com.rad.code.challenge.entities.country;

import java.util.List;

public interface countryServiceInterface {
    List<country> getAllCountry();
    country getCountryById(String id) throws Exception;
    int getCountryCount();
    String createCountry(country c);
    void deleteCountry(String name);
    List<country> getCountrysByFields(List<String> fieldS);
    List<country> getCountrysOver3M();
}