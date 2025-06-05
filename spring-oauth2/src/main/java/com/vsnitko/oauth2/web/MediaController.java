package com.vsnitko.oauth2.web;

import com.vsnitko.oauth2.service.S3Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

/**
 * @author v.snitko
 * @since 2025.08.12
 */
@Slf4j
@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    
    private final S3Service s3Service;

    @GetMapping("/photo/{name}")
    public ResponseEntity<?> getPhoto(@PathVariable String name, HttpServletResponse response) throws IOException {
        final ResponseInputStream<GetObjectResponse> s3Object = s3Service.getFile(name);
        response.setContentType("image/jpeg"); 
        s3Object.transferTo(response.getOutputStream());
      return ResponseEntity.ok(null);
    }
}
