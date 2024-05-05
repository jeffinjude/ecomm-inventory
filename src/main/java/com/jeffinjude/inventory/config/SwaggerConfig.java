package com.jeffinjude.inventory.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	    info = @Info(
	        title = "Inventory Service API",
	        description = "This api manages the inventory details of products.",
	        summary = "This api can add inventory details, get inventory details, increase product qunatity and decrease product quantity.",
	        contact = @Contact(
	        			name = "Jeffin Pulickal",
	        			email = "test@test.com"
	        		),
	        version = "v1"
	    )
	)
public class SwaggerConfig {
	//Access swagger at http://localhost:8094/swagger-ui/index.html
}
