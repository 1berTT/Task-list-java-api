package br.com.humbertodamasceno.task_list.modules.task.DTOs;

import lombok.Data;

import java.util.UUID;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import br.com.humbertodamasceno.task_list.modules.task.ENUMs.TaskStatus;
import br.com.humbertodamasceno.task_list.modules.task.ENUMs.TaskPriority;

import jakarta.validation.constraints.NotNull;

@Data
public class TaskFormatRequestDTO {

    @Length(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    @Length(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    private LocalDateTime completedAt;

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Category task ID is required")
    private UUID categoryTaskId;
}
