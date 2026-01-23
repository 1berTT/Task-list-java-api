package br.com.humbertodamasceno.task_list.modules.category_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.humbertodamasceno.task_list.modules.category_task.entities.CategoryTaskEntity;
import java.util.UUID;
import java.util.Optional;

public interface CategoryTaskRepository extends JpaRepository<CategoryTaskEntity, UUID> {
    Optional<CategoryTaskEntity> findByName(String name);

    Optional<CategoryTaskEntity> findById(UUID id);
}
