package org.example.free_new_magazine.service;

import org.example.free_new_magazine.entity.NewsletterSubscriber;
import org.example.free_new_magazine.exception.ConflictException;
import org.example.free_new_magazine.repository.NewsletterSubscriberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsletterSubscriberService {

    private final NewsletterSubscriberRepository subscriberRepository;

    public NewsletterSubscriberService(NewsletterSubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public List<NewsletterSubscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public NewsletterSubscriber addSubscriber(NewsletterSubscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public void removeSubscriber(Long id) {
        subscriberRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return subscriberRepository.existsByEmail(email);
    }

    public NewsletterSubscriber subscribe(NewsletterSubscriber subscriber) {
        if (subscriberRepository.existsByEmail(subscriber.getEmail())) {
            throw new ConflictException("This email is already subscribed.");
        }
        return subscriberRepository.save(subscriber);
    }
}
