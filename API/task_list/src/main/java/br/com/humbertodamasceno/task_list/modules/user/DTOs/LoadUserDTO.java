package br.com.humbertodamasceno.task_list.modules.user.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadUserDTO {
    private String id;
    private String name;
    private String email;
}
