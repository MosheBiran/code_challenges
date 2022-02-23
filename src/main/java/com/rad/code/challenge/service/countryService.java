package com.rad.code.challenge.service;

import com.mongodb.BasicDBObject;
import com.rad.code.challenge.entities.country;
import com.rad.code.challenge.entities.nameObject;
import com.rad.code.challenge.mongo.countryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import java.util.List;

@Service
public class countryService implements countryServiceInterface{
    @Autowired
    private countryRepository countryRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<country> getAllCountry() {
        return countryRepo.findAll();
    }

    @Override
    public country getCountryById(String id) throws Exception {
        return countryRepo.findById(id).orElseThrow(()->new Exception());
    }

    @Override
    public int getCountryCount() {
        return countryRepo.findAll().size();
    }

    @Override
    public String createCountry(country c) {
        countryRepo.save(c);
        return "";
    }

    @Override
    public void deleteCountry(String name) {

    }

    @Override
    public List<country> getCountrysByFields(List<String> fields) {

        Query query = new Query();
        for (String s:fields) {
            query.fields().include(s);
        }
        List<country> c = mongoTemplate.find(query,country.class,"country");
        return c;
    }


}
