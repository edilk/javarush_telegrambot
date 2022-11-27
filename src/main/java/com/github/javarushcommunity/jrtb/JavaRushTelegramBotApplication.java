package com.github.javarushcommunity.jrtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavaRushTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaRushTelegramBotApplication.class, args);
	}

}
