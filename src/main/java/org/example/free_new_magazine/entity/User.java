package org.example.free_new_magazine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = ("email")),
        @UniqueConstraint(columnNames = "username")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;



    @Column(columnDefinition = "TEXT")
    private String bio;


    @Column(columnDefinition = "TEXT")
    private String avatarImage;

    @Column(columnDefinition = "TEXT")
    private String bannerImage;

    private String color;


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
