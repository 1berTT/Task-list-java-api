package br.com.humbertodamasceno.task_list.modules.task.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;
import br.com.humbertodamasceno.task_list.modules.task.ENUMs.TaskStatus;
import br.com.humbertodamasceno.task_list.modules.task.ENUMs.TaskPriority;
import br.com.humbertodamasceno.task_list.modules.user.entitites.UserEntity;

@Entity
@Table(name = "task")
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Length(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @Length(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    @Column(name = "description", nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "completed_at", nullable = true)
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_task_id", nullable = false)
    private CategoryTaskEntity categoryTask;

}
