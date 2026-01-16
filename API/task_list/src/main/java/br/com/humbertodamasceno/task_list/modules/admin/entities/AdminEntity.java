package br.com.humbertodamasceno.task_list.modules.admin.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Length(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Email must be a valid email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
