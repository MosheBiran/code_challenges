package com.rad.code.challenge.mongo;

import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.repository.config.*;
import com.mongodb.*;
import com.mongodb.client.*;

@Configuration
@EnableMongoRepositories(basePackages = "com.rad.code.challenge")
public class MongoConfig
{
	public static final String	DB_NAME			= "challenge";
	private static final String	DB_HOST			= "localhost";
	private static final int	DB_PORT			= 27017;
	private static final String	DB_USER_PASS	= "";

	@Bean
	public MongoClient mongo() throws Exception
	{
		ConnectionString    connectionString    = new ConnectionString("mongodb://" + DB_USER_PASS + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
		return MongoClients.create(mongoClientSettings);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception
	{
		return new MongoTemplate(mongo(), DB_NAME);
	}
}