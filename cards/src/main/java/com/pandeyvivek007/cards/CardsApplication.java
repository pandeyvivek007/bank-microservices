package com.pandeyvivek007.cards;

import com.pandeyvivek007.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = CardsContactInfoDto.class)
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Microservice",
				version = "1.0",
				description = "This microservice handles customer cards, including cards creation, retrieval, and deletion.",
				contact = @Contact(
						name = "Vivek Pandey",
						email = "pandey@gmail.com",
						url = "https:linkedin.com/in/pandeyvivek007/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Accounts Microservice Documentation",
				url = "https://google.com/pandeyvivek007/accounts-microservice-docs/README.md"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
