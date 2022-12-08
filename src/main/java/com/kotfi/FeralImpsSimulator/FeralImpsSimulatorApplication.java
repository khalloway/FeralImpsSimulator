package com.kotfi.FeralImpsSimulator;

import com.kotfi.FeralImpsSimulator.repository.CardRepository;
import com.kotfi.FeralImpsSimulator.services.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeralImpsSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeralImpsSimulatorApplication.class, args);
	}

}
