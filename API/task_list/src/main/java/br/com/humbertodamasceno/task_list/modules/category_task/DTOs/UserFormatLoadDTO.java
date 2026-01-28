package br.com.humbertodamasceno.task_list.modules.category_task.DTOs;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFormatLoadDTO {
    private UUID id;
    private String name;
}
