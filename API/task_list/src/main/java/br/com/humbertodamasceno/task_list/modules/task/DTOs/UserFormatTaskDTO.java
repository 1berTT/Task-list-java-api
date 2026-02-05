package br.com.humbertodamasceno.task_list.modules.task.DTOs;

import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFormatTaskDTO {
    private UUID id;
    private String name;
}
