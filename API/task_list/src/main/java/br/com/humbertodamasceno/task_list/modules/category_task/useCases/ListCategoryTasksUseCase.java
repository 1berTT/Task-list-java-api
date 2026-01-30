package br.com.humbertodamasceno.task_list.modules.category_task.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.LoadCategoryTaskDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.PaginatedCategoryTaskDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.DTOs.UserFormatLoadDTO;
import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;
import br.com.humbertodamasceno.task_list.modules.category_task.repositories.CategoryTaskRepository;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListCategoryTasksUseCase {

    @Autowired
    private CategoryTaskRepository categoryTaskRepository;

    public PaginatedCategoryTaskDTO execute(Pageable pageable, UUID userId) {

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "name"));

        Page<CategoryTaskEntity> categoryTaskPage = this.categoryTaskRepository.findByUser_Id(userId, sortedPageable);

        var categoryTasks = categoryTaskPage.getContent().stream().map((categoryTask) -> {

            var user = UserFormatLoadDTO.builder()
                    .id(categoryTask.getUser().getId())
                    .name(categoryTask.getUser().getName())
                    .build();

            return LoadCategoryTaskDTO.builder()
                    .id(categoryTask.getId())
                    .name(categoryTask.getName())
                    .user(user)
                    .build();

        }).collect(Collectors.toList());

        return PaginatedCategoryTaskDTO.builder()
                .content(categoryTasks)
                .totalItems(categoryTaskPage.getTotalElements())
                .totalPages(categoryTaskPage.getTotalPages())
                .currentPage(categoryTaskPage.getNumber())
                .itemsPerPage(categoryTaskPage.getSize())
                .build();

    }
}
