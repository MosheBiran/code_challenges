package com.rad.code.challenge.countries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rad.code.challenge.entities.country;
import com.rad.code.challenge.mongo.countryRepository;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Configuration
public class CountriesDatabaseLoad
{
	public final String URL = "https://restcountries.com/v3.1/all";
	private static final Logger log = LoggerFactory.getLogger(CountriesDatabaseLoad.class);

	@Autowired
	private countryRepository countryRepo;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	@Bean
	CommandLineRunner initDatabaseCountries(MongoTemplate mongoTemplate)
	{
		return args -> {
			log.info("---------- start data extraction----------");

			//IMPLMENT YOUR CODE HERE
			//In order to work with countries data, 
			//we need to fetch this data form somewhere, 
			//convert it to Java object and store it at database

			JsonNode records_map = extractInfoFromDBHelpFunc();
			ArrayList<country> country_list = new ArrayList<>();
			records_map.forEach(node -> {
				try {
					country_list.add(new country(node));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			});
			countryRepo.deleteAll();// delete all country records
			countryRepo.saveAll(country_list);// save all country records

			log.info("---------- finish data extraction----------");

		};
	}


	/**
	 *---------------extractInfoFromDBHelpFunc---------------
	 * @return The information of the current database without any information surrounding it
	 */
	public JsonNode extractInfoFromDBHelpFunc() throws JsonProcessingException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			return root;
		}
		catch(Exception e) {
			log.info(e.toString());
			throw e;
		}
	}
}