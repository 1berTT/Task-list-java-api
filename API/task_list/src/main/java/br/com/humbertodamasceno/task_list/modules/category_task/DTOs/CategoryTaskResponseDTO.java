package br.com.humbertodamasceno.task_list.modules.category_task.DTOs;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTaskResponseDTO {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID userId;
}
