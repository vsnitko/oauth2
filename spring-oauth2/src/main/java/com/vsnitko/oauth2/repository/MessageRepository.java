package com.vsnitko.oauth2.repository;

import com.vsnitko.oauth2.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author v.snitko
 * @since 2025.04.08
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
