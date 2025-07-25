package com.ta2khu75.quiz.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
	private List<String> MEDIA_TYPES = Arrays.asList(MediaType.APPLICATION_JSON_VALUE);

	@Override
	public void addCorsMappings(@NonNull CorsRegistry registry) {
		registry.addMapping("/api/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*")
				.exposedHeaders("www-authenticate").allowCredentials(true);
	}

	@Bean
	AuditorAware<String> auditorAware() {
		return new ApplicationAuditAware();
	}

	@Bean
	EndpointMediaTypes endpointMediaTypes() {
		return new EndpointMediaTypes(MEDIA_TYPES, MEDIA_TYPES);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new SnakeCaseModelAttributeResolver());
	}
}
