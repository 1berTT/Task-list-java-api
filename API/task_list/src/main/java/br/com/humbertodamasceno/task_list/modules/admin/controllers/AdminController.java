package br.com.humbertodamasceno.task_list.modules.admin.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.humbertodamasceno.task_list.modules.admin.entities.AdminEntity;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.CreateAdminUseCase;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.LoginAdminUseCase;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.UpdateAdminUseCase;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.LoadUsersUseCase;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.DeleteUserUseCase;
import br.com.humbertodamasceno.task_list.modules.admin.useCases.LoadAdminUseCase;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import br.com.humbertodamasceno.task_list.modules.admin.DTOs.AdminLoginRequestDTO;
import br.com.humbertodamasceno.task_list.modules.admin.DTOs.LoadAdminDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import br.com.humbertodamasceno.task_list.exceptions.AdminRuntimeExceptions;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CreateAdminUseCase createAdminUseCase;

    @Autowired
    private LoginAdminUseCase loginAdminUseCase;

    @Autowired
    private LoadUsersUseCase loadUsersUseCase;

    @Autowired
    private DeleteUserUseCase deleteUserUseCase;

    @Autowired
    private UpdateAdminUseCase updateAdminUseCase;

    @Autowired
    private LoadAdminUseCase loadAdminUseCase;

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

    @GetMapping("/load-users")
    public ResponseEntity<Object> loadUsers() {
        try {
            var result = this.loadUsersUseCase.execute();
            return ResponseEntity.ok().body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID userId) {
        try {
            this.deleteUserUseCase.execute(userId);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAdmin(@Valid @RequestBody LoadAdminDTO loadAdminDTO,
            HttpServletRequest httpServletRequest) {
        try {
            if (httpServletRequest.getAttribute("admin_id") == null
                    || httpServletRequest.getAttribute("admin_id").equals(loadAdminDTO.getId())) {
                throw new AdminRuntimeExceptions("You are not authorized to update this admin");
            }

            var result = this.updateAdminUseCase.execute(loadAdminDTO);

            return ResponseEntity.ok().body(result);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/load/{adminId}")
    public ResponseEntity<Object> loadAdmin(@PathVariable UUID adminId, HttpServletRequest httpServletRequest) {
        try {
            if (httpServletRequest.getAttribute("admin_id") == null
                    || !httpServletRequest.getAttribute("admin_id").equals(String.valueOf(adminId))) {
                throw new AdminRuntimeExceptions("You are not authorized to load this admin");
            }

            var result = this.loadAdminUseCase.execute(adminId);
            return ResponseEntity.ok().body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
