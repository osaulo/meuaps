package com.meuaps;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}

	/**
	 * bean config openapi
	 * @param appVersion
	 * @return
	 */
	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		// create OpenAPI doc
		return new OpenAPI().info(new Info().title("Meu Aps API")
				.version(appVersion)
				.description("Meu aps api.")
				.termsOfService("http://swagger.io/terms/")
				.contact(new Contact().name("Saulo de Tarso").url("https://www.linkedin.com/in/saulo-de-tarso-b5217932/").email("osaulo_tarso@hotmail.com"))
				.license(new License().name("Apache 2.0")
						.url("http://springdoc.org")));
	}

}
