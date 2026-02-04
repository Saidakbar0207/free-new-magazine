package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.ContactMessageDTO;
import org.example.free_new_magazine.entity.ContactMessage;
import org.example.free_new_magazine.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessage  create(ContactMessageDTO dto){
        ContactMessage msg = ContactMessage.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .imageUrl(dto.getImageUrl())
                .build();
        return contactMessageRepository.save(msg);
    }

}
