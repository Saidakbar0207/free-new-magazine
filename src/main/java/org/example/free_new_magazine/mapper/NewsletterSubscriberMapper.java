package org.example.free_new_magazine.mapper;

import org.example.free_new_magazine.dto.NewsletterSubscriberDTO;
import org.example.free_new_magazine.entity.NewsletterSubscriber;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsletterSubscriberMapper {
    NewsletterSubscriberDTO toDTO(NewsletterSubscriber subscriber);
    NewsletterSubscriber toEntity(NewsletterSubscriberDTO subscriberDTO);
}
