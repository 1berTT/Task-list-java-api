package br.com.humbertodamasceno.task_list.modules.user.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoadUserDTO;
import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoginUserRequestDTO;
import br.com.humbertodamasceno.task_list.modules.user.entitites.UserEntity;
import br.com.humbertodamasceno.task_list.modules.user.useCases.CreateUserUseCase;
import br.com.humbertodamasceno.task_list.modules.user.useCases.LoginUserUseCase;
import br.com.humbertodamasceno.task_list.modules.user.useCases.LoadUserUseCase;
import br.com.humbertodamasceno.task_list.modules.user.useCases.UpdateUserUseCase;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private LoginUserUseCase loginUserUseCase;

    @Autowired
    private LoadUserUseCase loadUserUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity userEntity) {
        try {
            var result = this.createUserUseCase.execute(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginUserRequestDTO loginUserRequestDTO) {
        try {
            var result = this.loginUserUseCase.execute(loginUserRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }
    }

    @GetMapping("/load/{userId}")
    public ResponseEntity<Object> loadUser(@PathVariable UUID userId, HttpServletRequest request) {
        try {
            var authenticatedUserId = (String) request.getAttribute("user_id");

            if (authenticatedUserId == null || !authenticatedUserId.equals(userId.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied! You can only load your own information.");
            }

            var result = this.loadUserUseCase.execute(userId);
            return ResponseEntity.ok().body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody LoadUserDTO userRequest, HttpServletRequest request) {
        try {
            var authenticatedUserId = (String) request.getAttribute("user_id");
            if (authenticatedUserId == null || !authenticatedUserId.equals(userRequest.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access denied! You can only update your own information.");
            }
            var result = this.updateUserUseCase.execute(userRequest);
            return ResponseEntity.ok().body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
