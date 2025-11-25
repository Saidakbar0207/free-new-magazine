package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.NewsletterSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsletterSubscriberRepository  extends JpaRepository<NewsletterSubscriber,Long> {

    boolean existsByEmail(String email);
}
