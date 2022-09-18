package agent.agentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class AgentAppApplication {

	@Value("${cors.origin}")
	private String corsOrigin;

	public static void main(String[] args) {
		SpringApplication.run(AgentAppApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(corsOrigin, "http://localhost:8081", "http://localhost:8084",
						"http://localhost:8081/job-offer-service");
			}
		};
	}

}
