package br.com.humbertodamasceno.task_list.modules.category_task.DTOs;

import jakarta.validation.constraints.NotBlank;

public record CategoryTaskRequestDTO(@NotBlank String name) {

}
