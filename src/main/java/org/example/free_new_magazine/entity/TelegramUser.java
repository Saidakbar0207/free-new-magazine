package org.example.free_new_magazine.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "telegram_users", indexes = {
        @Index(name = "idx_chat_id", columnList = "chatId")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long chatId;

    private String username;
    private String firstName;
    private String language;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean subscribed = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}