package com.goldeelox.mtg.MTGDeckBuilder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**")
	    .allowedOriginPatterns(
	      "https://mtg.goldeelox.com",
	      "https://exquisite-cupcake-4a3ed2.netlify.app"
	    )
	    .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
	    .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
	    .allowCredentials(true);
	}
}