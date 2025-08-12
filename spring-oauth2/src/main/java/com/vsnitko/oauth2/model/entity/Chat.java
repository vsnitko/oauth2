package com.vsnitko.oauth2.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author v.snitko
 * @since 2025.03.03
 */
@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    private String avatar;

    @ManyToMany(mappedBy = "chats")
    private Set<User> subscribers;
    
    @OneToMany
    @JoinColumn(name = "chat_id")
    private List<Message> messages = new ArrayList<>();

}
