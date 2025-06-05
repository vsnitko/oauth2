package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.config.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

/**
 * @author v.snitko
 * @since 2025.08.12
 */
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public void uploadFile(String key, byte[] file) throws IOException {
        final PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(key)
                .contentType("image/jpeg")
                .build();
        final RequestBody requestBody = RequestBody.fromBytes(file);
        s3Client.putObject(request, requestBody);
    }
    
    public ResponseInputStream<GetObjectResponse> getFile(String name) throws IOException {
        final GetObjectRequest request = GetObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(name)
                .build();
        return s3Client.getObject(request);
    }
}
