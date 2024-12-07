package com.example.demo.config;

import com.example.demo.services.AstroInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@Configuration
public class AppConfig {

    @Bean
    public RestTemplate astroRestTemplate(RestTemplateBuilder builder,
                                          @Value("${astro.baseUrl}") String baseUri) {
        return builder.rootUri(baseUri).build();
    }

    @Bean
    public RestTemplate anotherRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://someaddres").build();
    }

    @Bean
    public AstroInterface astroInterface(@Value("${astro.baseUrl}") String baseUrl) {
        WebClient client = WebClient.create(baseUrl);
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client))
                        .build();
        return factory.createClient(AstroInterface.class);
    }
}
