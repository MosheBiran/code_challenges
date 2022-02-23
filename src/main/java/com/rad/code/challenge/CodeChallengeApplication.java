package com.rad.code.challenge;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class CodeChallengeApplication
{
	protected Logger	logger	= LoggerFactory.getLogger(this.getClass());

	@Value("${info.app.name:Unknown}")
	protected String	applicationName;

	@Value("${info.app.host:Unknown}")
	protected String	host;

	@Value("${info.app.port:Unknown}")
	protected String	port;

	@Value("${java.specification.version}")
	protected String	javaVersion;
	
	public static void main(String[] args)
	{
		SpringApplication.run(CodeChallengeApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext appContext)
	{
		return args -> {
			logger.info("************************************************");
			logger.info("Name: {}", applicationName);
			logger.info("HOST: {}", host);
			logger.info("PORT: {}", port);
			logger.info("Java Version: {}", javaVersion);
			logger.info("{} is Ready", applicationName.toUpperCase());
			logger.info("************************************************");
		};
	}
}