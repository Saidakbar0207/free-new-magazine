package org.example.free_new_magazine.dto;


public record TelegramPostDTO(
        Long id,
        String title,
        String content,
        String url
) {}