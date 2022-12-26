package com.vsnitko.oauth2;

import com.vsnitko.oauth2.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author v.snitko
 * @since 2022.12.17
 */
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Oauth2Application {

  public static void main(String[] args) {
    SpringApplication.run(Oauth2Application.class, args);
  }
}
