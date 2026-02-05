package br.com.humbertodamasceno.task_list.modules.category_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;
import java.util.UUID;
import java.util.Optional;

public interface CategoryTaskRepository extends JpaRepository<CategoryTaskEntity, UUID> {
    Optional<CategoryTaskEntity> findByName(String name);

    Optional<CategoryTaskEntity> findById(UUID id);

    Optional<CategoryTaskEntity> findByNameAndUser_Id(String name, UUID userId);

    Optional<CategoryTaskEntity> findByIdAndUser_Id(UUID id, UUID userId);

    Page<CategoryTaskEntity> findByUser_Id(UUID userId, Pageable pageable);
}
