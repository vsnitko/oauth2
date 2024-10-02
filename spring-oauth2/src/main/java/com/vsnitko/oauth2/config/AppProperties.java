package com.vsnitko.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String clientPath;
    private String serverPath;
    private String clientOauth2RedirectEndpoint;
}
