package com.vsnitko.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import static java.net.URI.create;

/**
 * @author v.snitko
 * @since 2025.08.12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "s3")
public class S3Properties {

    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucket;

    @Bean
    public S3Client s3Client() {
        final AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .endpointOverride(create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.EU_CENTRAL_1)
                .build();
    }
}
