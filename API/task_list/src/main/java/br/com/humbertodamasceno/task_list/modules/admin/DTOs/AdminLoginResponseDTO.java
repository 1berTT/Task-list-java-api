package br.com.humbertodamasceno.task_list.modules.admin.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginResponseDTO {
    private String access_token;
    private Long expires_in;
}
