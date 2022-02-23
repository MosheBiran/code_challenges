package com.rad.code.challenge.controller;

import com.rad.code.challenge.entities.country;
import com.rad.code.challenge.service.countryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class apiController {

    @Autowired
    private countryServiceInterface countryService;

    @GetMapping("/country")
    public List<country> getAllcountryInfo(){
        return countryService.getAllCountry();
    }

    @GetMapping("/country/fields={fields}")
    public List<country> getCountrysByFields(@PathVariable(value = "fields") String fields) {
        List<String> field_to = List.of(fields.split("[ .,]+"));
        return countryService.getCountrysByFields(field_to);
    }

    @GetMapping("/country/300million")
    public List<country> getCountrysByFields() {
        return countryService.getCountrysOver3M();
    }

    @GetMapping("/country/maxarea")
    public country getBiggest_Countrys() {
        return countryService.getBiggestCountry();
    }

    @GetMapping("/country/{id}")
    public country getCountryBy_id(@PathVariable(value = "id") String id) throws Exception {
        return countryService.getCountryById(id);
    }

    @GetMapping("/country/count")
    public int getCountry_count() {
        return countryService.getCountryCount();
    }

    @PostMapping("/country")
    public String createNewCountry(@RequestBody country c){
        return countryService.createCountry(c);
    }




}
