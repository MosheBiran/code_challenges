package com.rad.code.challenge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@ToString

@Document
public class country {
    long population;
    double area;
    String region;
    nameObject name;

    //Default constructor
    public country(){
    }

    //constructor that receives a JsonNode and breaks it down according to the fields.
    public country(JsonNode countryInfo) throws JsonProcessingException {
        this.population = countryInfo.get("population").asInt();
        this.area = countryInfo.get("area").asDouble();
        this.region = countryInfo.get("region").asText();
        this.name = new nameObject(countryInfo);
    }

}
