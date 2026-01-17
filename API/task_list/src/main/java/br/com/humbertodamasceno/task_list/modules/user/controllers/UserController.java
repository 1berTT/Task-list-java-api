package br.com.humbertodamasceno.task_list.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import br.com.humbertodamasceno.task_list.modules.user.entitites.UserEntity;
import br.com.humbertodamasceno.task_list.modules.user.useCases.CreateUserUseCase;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity userEntity) {
        try {
            var result = this.createUserUseCase.execute(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
