package br.com.humbertodamasceno.task_list.modules.category_task.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedCategoryTaskDTO {
    private List<LoadCategoryTaskDTO> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int itemsPerPage;

}
