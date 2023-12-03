package com.vsnitko.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author v.snitko
 * @since 2023.12.03
 */
@Configuration
public class RestConfig {

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}
