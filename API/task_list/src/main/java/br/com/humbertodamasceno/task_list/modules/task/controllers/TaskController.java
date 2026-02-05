package br.com.humbertodamasceno.task_list.modules.task.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import br.com.humbertodamasceno.task_list.modules.task.DTOs.TaskFormatRequestDTO;
import br.com.humbertodamasceno.task_list.modules.task.useCases.CreateTaskUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/task")
public class TaskController {

    @Autowired
    private CreateTaskUseCase createTaskUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createTask(@Valid @RequestBody TaskFormatRequestDTO taskFormatRequestDTO,
            HttpServletRequest httpServletRequest) {

        try {
            var userId = httpServletRequest.getAttribute("user_id");
            if (userId == null || taskFormatRequestDTO.getUserId() == null
                    || !UUID.fromString((String) userId).equals(taskFormatRequestDTO.getUserId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            var result = this.createTaskUseCase.execute(taskFormatRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
