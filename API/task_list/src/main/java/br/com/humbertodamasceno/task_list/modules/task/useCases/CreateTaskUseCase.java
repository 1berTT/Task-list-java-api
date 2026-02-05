package br.com.humbertodamasceno.task_list.modules.task.useCases;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.humbertodamasceno.task_list.modules.task.DTOs.CategoryTaskFormatDTO;
import br.com.humbertodamasceno.task_list.modules.task.DTOs.LoadTaskFormatDTO;
import br.com.humbertodamasceno.task_list.modules.task.entities.TaskEntity;
import br.com.humbertodamasceno.task_list.modules.task.repositories.TaskRepository;
import br.com.humbertodamasceno.task_list.exceptions.TaskRuntimeExcepetions;
import br.com.humbertodamasceno.task_list.modules.category_task.repositories.CategoryTaskRepository;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;
import br.com.humbertodamasceno.task_list.modules.task.DTOs.TaskFormatRequestDTO;
import br.com.humbertodamasceno.task_list.modules.task.DTOs.UserFormatTaskDTO;

@Service
public class CreateTaskUseCase {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryTaskRepository categoryTaskRepository;

    @Autowired
    private UserRepository userRepository;

    public LoadTaskFormatDTO execute(TaskFormatRequestDTO taskFormatRequestDTO) {

        var user = this.userRepository.findById(taskFormatRequestDTO.getUserId()).orElseThrow(() -> {
            throw new TaskRuntimeExcepetions("User not found");
        });

        var categoryTask = this.categoryTaskRepository
                .findByIdAndUser_Id(taskFormatRequestDTO.getCategoryTaskId(), taskFormatRequestDTO.getUserId())
                .orElseThrow(() -> {
                    throw new TaskRuntimeExcepetions("Category task not found for the user");
                });

        this.taskRepository.findByTitleAndUser_Id(taskFormatRequestDTO.getTitle(), taskFormatRequestDTO.getUserId())
                .ifPresent((task) -> {
                    throw new TaskRuntimeExcepetions("Task already exists for the user");
                });

        // Prazo vale pelo dia; ignora a hora (ex.: 2026-02-10T00:00:00 = dia 10
        // inteiro)
        if (taskFormatRequestDTO.getDueDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new TaskRuntimeExcepetions("Due date cannot be in the past");
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskFormatRequestDTO.getTitle());
        taskEntity.setDescription(taskFormatRequestDTO.getDescription());
        taskEntity.setStatus(taskFormatRequestDTO.getStatus());
        taskEntity.setPriority(taskFormatRequestDTO.getPriority());
        taskEntity.setDueDate(taskFormatRequestDTO.getDueDate());
        taskEntity.setCompletedAt(taskFormatRequestDTO.getCompletedAt());
        taskEntity.setUser(user);
        taskEntity.setCategoryTask(categoryTask);

        var taskSaved = this.taskRepository.save(taskEntity);

        UserFormatTaskDTO userFormatTaskDTO = UserFormatTaskDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

        CategoryTaskFormatDTO categoryTaskFormatDTO = CategoryTaskFormatDTO.builder()
                .id(categoryTask.getId())
                .name(categoryTask.getName())
                .build();

        LoadTaskFormatDTO loadTaskFormatDTO = LoadTaskFormatDTO.builder()
                .id(taskSaved.getId())
                .title(taskSaved.getTitle())
                .description(taskSaved.getDescription())
                .status(taskSaved.getStatus())
                .priority(taskSaved.getPriority())
                .dueDate(taskSaved.getDueDate())
                .completedAt(taskSaved.getCompletedAt())
                .user(userFormatTaskDTO)
                .categoryTask(categoryTaskFormatDTO)
                .build();

        return loadTaskFormatDTO;

    }

}
