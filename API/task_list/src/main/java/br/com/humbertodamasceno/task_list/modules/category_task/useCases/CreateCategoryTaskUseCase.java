package br.com.humbertodamasceno.task_list.modules.category_task.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.exceptions.CategoryTaskExceptions;
import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.category_task.repositories.CategoryTaskRepository;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.CategoryTaskResponseDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;

@Service
public class CreateCategoryTaskUseCase {

    @Autowired
    private CategoryTaskRepository categoryTaskRepository;

    @Autowired
    private UserRepository userRepository;

    public CategoryTaskResponseDTO execute(String name, UUID userId) {
        this.categoryTaskRepository
                .findByNameAndUser_Id(name, userId)
                .ifPresent((category) -> {
                    throw new CategoryTaskExceptions("Category task already exists");
                });

        var user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new UserRuntimeExceptions("User not found");
        });

        CategoryTaskEntity categoryTaskEntity = new CategoryTaskEntity();
        categoryTaskEntity.setName(name);
        categoryTaskEntity.setUser(user);
        var categoryTask = this.categoryTaskRepository.save(categoryTaskEntity);

        return CategoryTaskResponseDTO.builder()
                .id(categoryTask.getId())
                .name(categoryTask.getName())
                .createdAt(categoryTask.getCreatedAt())
                .updatedAt(categoryTask.getUpdatedAt())
                .userId(categoryTask.getUser().getId())
                .build();

    }
}
