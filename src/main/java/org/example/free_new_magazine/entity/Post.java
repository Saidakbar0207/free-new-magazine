package org.example.free_new_magazine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name="cover_type", length=10)
    private CoverType coverType = CoverType.NONE;

    @Column(name="cover_url", columnDefinition="TEXT")
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @ToString.Exclude
    private User author;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    List<PostImage> images;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    List<PostVideo> videos;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    List<PostTag> postTags;


    private Long views = 0L;

    private Boolean featured = false;

    @Enumerated(EnumType.STRING)

    private PostStatus status;



    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist
    public void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = createAt;
    }
    @PreUpdate
    public void onUpdate() {
        updateAt = LocalDateTime.now();
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Post post = (Post) o;
        return getId() != null && Objects.equals(getId(), post.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }


}
