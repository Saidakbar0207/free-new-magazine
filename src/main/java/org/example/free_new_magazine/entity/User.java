package org.example.free_new_magazine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(unique = true)
    private String username;

    private String phone;

    private String bio;

    private String website;

    private String avatarImage;

    private String bannerImage;

    private String color;

    private String explanation;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private Boolean  isActive = true;
    @Builder.Default
    private Boolean isDeleted = false;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @PrePersist
    public void onCreate(){
        createdDate = LocalDateTime.now();
        updatedDate = createdDate;
    }
    @PreUpdate
    public void onUpdate(){
        updatedDate = LocalDateTime.now();
    }





    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
