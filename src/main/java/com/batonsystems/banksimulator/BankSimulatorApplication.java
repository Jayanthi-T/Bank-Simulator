package com.batonsystems.banksimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJpaRepositories
@EnableJms
@ComponentScan
public class BankSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSimulatorApplication.class, args);
	}

}
