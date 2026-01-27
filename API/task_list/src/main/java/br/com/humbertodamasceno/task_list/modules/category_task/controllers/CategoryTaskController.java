package br.com.humbertodamasceno.task_list.modules.category_task.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.CategoryTaskRequestDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.useCases.CreateCategoryTaskUseCase;
import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;

@RestController
@RequestMapping("/user/category")
public class CategoryTaskController {

    @Autowired
    private CreateCategoryTaskUseCase createCategoryTaskUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> createCategoryTask(@RequestBody CategoryTaskRequestDTO categoryTaskRequestDTO,
            HttpServletRequest request) {
        try {
            var userId = request.getAttribute("user_id");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            var result = this.createCategoryTaskUseCase.execute(categoryTaskRequestDTO.name(),
                    UUID.fromString((String) userId));

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
