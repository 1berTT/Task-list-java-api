package br.com.humbertodamasceno.task_list.modules.category_task.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryTaskDTO(@NotBlank(message = "Name is required") String name,
                @NotBlank(message = "User ID is required") UUID userId) {

}
