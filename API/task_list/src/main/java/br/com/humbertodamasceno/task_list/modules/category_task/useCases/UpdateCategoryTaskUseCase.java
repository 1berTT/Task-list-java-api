package br.com.humbertodamasceno.task_list.modules.category_task.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.category_task.repositories.CategoryTaskRepository;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.UpdateCategoryTaskDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.UserFormatLoadDTO;
import br.com.humbertodamasceno.task_list.exceptions.CategoryTaskExceptions;
import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.LoadCategoryTaskDTO;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;

@Service
public class UpdateCategoryTaskUseCase {

    @Autowired
    private CategoryTaskRepository categoryTaskRepository;

    @Autowired
    private UserRepository userRepository;

    public LoadCategoryTaskDTO execute(UpdateCategoryTaskDTO updateCategoryTaskDTO, String id) {

        var user = this.userRepository.findById(updateCategoryTaskDTO.userId()).orElseThrow(() -> {
            throw new UserRuntimeExceptions("User not found");
        });

        var categoryTask = this.categoryTaskRepository.findById(UUID.fromString(id)).orElseThrow(() -> {
            throw new CategoryTaskExceptions("Category task not found");
        });

        categoryTask.setName(updateCategoryTaskDTO.name());
        categoryTask.setUser(user);

        var categoryTaskResult = this.categoryTaskRepository.save(categoryTask);

        var userFormatLoadDTO = UserFormatLoadDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

        var loadCategoryTaskDTOResult = LoadCategoryTaskDTO.builder()
                .id(categoryTaskResult.getId())
                .name(categoryTaskResult.getName())
                .user(userFormatLoadDTO)
                .build();

        return loadCategoryTaskDTOResult;

    }

}
