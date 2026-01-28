package br.com.humbertodamasceno.task_list.modules.category_task.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.CategoryTaskRequestDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.UpdateCategoryTaskDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.useCases.CreateCategoryTaskUseCase;
import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;
import br.com.humbertodamasceno.task_list.modules.category_task.useCases.LoadCategoryTaskByIdUseCase;
import br.com.humbertodamasceno.task_list.modules.category_task.useCases.UpdateCategoryTaskUseCase;

@RestController
@RequestMapping("/user/category")
public class CategoryTaskController {

    @Autowired
    private CreateCategoryTaskUseCase createCategoryTaskUseCase;

    @Autowired
    private LoadCategoryTaskByIdUseCase loadCategoryTaskByIdUseCase;

    @Autowired
    private UpdateCategoryTaskUseCase updateCategoryTaskUseCase;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> loadCategoryTaskById(@PathVariable String id, HttpServletRequest request) {
        try {
            var userId = request.getAttribute("user_id");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            var result = this.loadCategoryTaskByIdUseCase.execute(UUID.fromString(id),
                    UUID.fromString((String) userId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategoryTask(@RequestBody UpdateCategoryTaskDTO updateCategoryTaskDTO,
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            var userId = request.getAttribute("user_id");
            if (userId == null || !UUID.fromString((String) userId).equals(updateCategoryTaskDTO.userId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("You are not authorized to update this category task");
            }
            var result = this.updateCategoryTaskUseCase.execute(updateCategoryTaskDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
