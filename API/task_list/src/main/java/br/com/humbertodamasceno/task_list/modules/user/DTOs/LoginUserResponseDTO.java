package br.com.humbertodamasceno.task_list.modules.user.DTOs;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponseDTO {
    private String access_token;
    private Long expires_in;
}
