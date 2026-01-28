package br.com.humbertodamasceno.task_list.modules.category_task.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import br.com.humbertodamasceno.task_list.exceptions.CategoryTaskExceptions;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.LoadCategoryTaskDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.UserFormatLoadDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.repositories.CategoryTaskRepository;

@Service
public class LoadCategoryTaskByIdUseCase {

    @Autowired
    private CategoryTaskRepository categoryTaskRepository;

    public LoadCategoryTaskDTO execute(UUID id, UUID userId) {
        var categoryTask = this.categoryTaskRepository.findById(id).orElseThrow(() -> {
            throw new CategoryTaskExceptions("Category task not found");
        });

        var taskUserId = categoryTask.getUser() != null ? categoryTask.getUser().getId() : null;
        if (!Objects.equals(taskUserId, userId)) {
            throw new CategoryTaskExceptions("You are not authorized to access this category task");
        }

        UserFormatLoadDTO userFormatLoadDTO = UserFormatLoadDTO.builder()
                .id(categoryTask.getUser().getId())
                .name(categoryTask.getUser().getName())
                .build();

        LoadCategoryTaskDTO loadCategoryTaskDTO = LoadCategoryTaskDTO.builder()
                .id(categoryTask.getId())
                .name(categoryTask.getName())
                .user(userFormatLoadDTO)
                .build();

        return loadCategoryTaskDTO;
    }

}
