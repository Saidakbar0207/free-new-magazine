package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.NewsletterSubscriber;
import org.example.free_new_magazine.service.NewsletterSubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
public class NewsletterSubscriberController {

    private final NewsletterSubscriberService service;

    @PostMapping("/subscribe")
    public ResponseEntity<NewsletterSubscriber> subscribe(@RequestBody NewsletterSubscriber subscriber) {
        NewsletterSubscriber saved = service.subscribe(subscriber);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsletterSubscriber>> getAllSubscribers() {
        return ResponseEntity.ok(service.getAllSubscribers());
    }
}
