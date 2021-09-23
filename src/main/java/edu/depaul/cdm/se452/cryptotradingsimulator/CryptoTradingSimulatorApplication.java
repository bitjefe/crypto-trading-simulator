package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CryptoTradingSimulatorApplication {
	private static final Logger log = LoggerFactory.getLogger(CryptoTradingSimulatorApplication.class);

	@Bean
	public CommandLineRunner printHello() {
		return (args) -> {
			log.info("Hello world");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingSimulatorApplication.class, args);
	}

}
