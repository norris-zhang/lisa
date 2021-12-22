package com.norriszhang.lisa;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LisaApplication {

	private final String allowedOrigins;

	public LisaApplication(@Value("${lisa.allowedOrigins}") String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	public static void main(String[] args) {
		SpringApplication.run(LisaApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				if (!StringUtils.isEmpty(allowedOrigins)) {
					registry.addMapping("/api/**")
							.allowedOrigins(allowedOrigins.split(","));
				}
			}
		};
	}

}
