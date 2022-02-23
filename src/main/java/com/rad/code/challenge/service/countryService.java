package com.rad.code.challenge.service;

import com.mongodb.BasicDBObject;
import com.rad.code.challenge.entities.country;
import com.rad.code.challenge.entities.nameObject;
import com.rad.code.challenge.mongo.countryRepository;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mongodb.client.model.Aggregates.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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

    public List<country> getCountrysOver3M() {
        Query query = new Query();
        query.addCriteria(new Criteria().where("population").gt(300000000));
        return mongoTemplate.find(query,country.class,"country");
        }

    public country getBiggestCountry() {
    final Aggregation aggregation = newAggregation(
            Aggregation.sort(Sort.Direction.DESC, "area")
    );
    AggregationResults<country> results = mongoTemplate.aggregate(aggregation, "country", country.class);
    return results.getMappedResults().get(0);
    }
}
