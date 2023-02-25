package harugreen.harugreenserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HarugreenServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarugreenServerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://harugreen.vercel.app", "http://localhost:3000")
						.allowedMethods("GET", "POST")
						.exposedHeaders("X-AUTH-TOKEN", "X-AUTH-REFRESH", "X-AUTH-GRANT")
						.allowCredentials(true);
			}
		};
	}

}

