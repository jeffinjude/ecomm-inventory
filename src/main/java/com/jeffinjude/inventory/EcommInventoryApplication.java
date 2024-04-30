package com.jeffinjude.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EcommInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommInventoryApplication.class, args);
	}

}
