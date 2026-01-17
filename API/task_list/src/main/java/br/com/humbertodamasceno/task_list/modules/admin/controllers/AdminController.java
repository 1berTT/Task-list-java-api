package br.com.humbertodamasceno.task_list.modules.admin.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.humbertodamasceno.task_list.modules.admin.entities.AdminEntity;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.CreateAdminUseCase;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.LoginAdminUseCase;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import br.com.humbertodamasceno.task_list.modules.admin.DTOs.AdminLoginRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CreateAdminUseCase createAdminUseCase;

    @Autowired
    private LoginAdminUseCase loginAdminUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createAdmin(@Valid @RequestBody AdminEntity adminEntity) {
        try {
            var result = this.createAdminUseCase.execute(adminEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginAdmin(@RequestBody AdminLoginRequestDTO adminLoginRequestDTO) {
        try {
            var result = this.loginAdminUseCase.execute(adminLoginRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }
    }

}
