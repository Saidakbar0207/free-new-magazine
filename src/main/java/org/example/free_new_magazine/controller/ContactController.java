package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.ContactMessage;
import org.example.free_new_magazine.repository.ContactMessageRepository;
import org.example.free_new_magazine.service.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactMessageRepository contactRepo;
    private final StorageService storageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> send(
            @RequestParam String subject,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String message,
            @RequestPart(required = false) MultipartFile image
    ) {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            imageUrl = storageService.save(image, "contact");
        }

        ContactMessage cm = ContactMessage.builder()
                .subject(subject)
                .name(name)
                .email(email)
                .message(message)
                .imageUrl(imageUrl)
                .build();

        contactRepo.save(cm);

        return ResponseEntity.ok(Map.of(
                "id", cm.getId(),
                "message", "Xabar qabul qilindi",
                "imageUrl", cm.getImageUrl()
        ));
    }
}
