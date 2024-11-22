package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
public class CloudRentalProjectApplication {

	
	public static void main(String[] args) {
		System.out.println("ddddddddddd");
		SpringApplication.run(CloudRentalProjectApplication.class, args);
		
	}
	

	
	
}
