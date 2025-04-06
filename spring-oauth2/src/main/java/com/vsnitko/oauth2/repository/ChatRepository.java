package com.vsnitko.oauth2.repository;

import com.vsnitko.oauth2.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
