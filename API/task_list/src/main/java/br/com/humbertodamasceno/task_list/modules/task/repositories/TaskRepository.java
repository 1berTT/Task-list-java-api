package br.com.humbertodamasceno.task_list.modules.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.humbertodamasceno.task_list.modules.task.entities.TaskEntity;
import java.util.UUID;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    Optional<TaskEntity> findByTitle(String title);

    Optional<TaskEntity> findById(UUID id);

    Optional<TaskEntity> findByTitleAndUser_Id(String title, UUID userId);

}
