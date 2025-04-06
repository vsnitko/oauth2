package com.vsnitko.oauth2.repository;

import com.vsnitko.oauth2.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author v.snitko
 * @since 2025.03.23
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
