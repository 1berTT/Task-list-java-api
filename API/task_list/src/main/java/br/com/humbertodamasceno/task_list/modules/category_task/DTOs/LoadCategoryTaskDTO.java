package br.com.humbertodamasceno.task_list.modules.category_task.DTOs;

import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadCategoryTaskDTO {
    private UUID id;
    private String name;
    private UserFormatLoadDTO user;
}
