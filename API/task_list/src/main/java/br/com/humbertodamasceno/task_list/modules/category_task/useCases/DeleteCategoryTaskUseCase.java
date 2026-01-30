package br.com.humbertodamasceno.task_list.modules.category_task.useCases;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.exceptions.CategoryTaskExceptions;
import br.com.humbertodamasceno.task_list.modules.category_task.repositories.CategoryTaskRepository;

@Service
public class DeleteCategoryTaskUseCase {

    @Autowired
    private CategoryTaskRepository categoryTaskRepository;

    public void execute(UUID categoryTaskId, UUID userId) {
        var categoryTask = this.categoryTaskRepository.findById(categoryTaskId).orElseThrow(() -> {
            throw new CategoryTaskExceptions("Category task not found");
        });

        var taskUserId = categoryTask.getUser() != null ? categoryTask.getUser().getId() : null;
        if (!Objects.equals(taskUserId, userId)) {
            throw new CategoryTaskExceptions("You are not authorized to delete this category task");
        }

        this.categoryTaskRepository.delete(categoryTask);
    }

}
