package br.com.humbertodamasceno.task_list.modules.task.DTOs;

import java.util.UUID;

import br.com.humbertodamasceno.task_list.modules.task.ENUMs.TaskPriority;
import br.com.humbertodamasceno.task_list.modules.task.ENUMs.TaskStatus;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadTaskFormatDTO {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    private UserFormatTaskDTO user;
    private CategoryTaskFormatDTO categoryTask;
}
