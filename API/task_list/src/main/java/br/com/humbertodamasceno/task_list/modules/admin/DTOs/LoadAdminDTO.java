package br.com.humbertodamasceno.task_list.modules.admin.DTOs;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadAdminDTO {

    @Length(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Email(message = "Email must be a valid email")
    private String email;

    private UUID id;
}
