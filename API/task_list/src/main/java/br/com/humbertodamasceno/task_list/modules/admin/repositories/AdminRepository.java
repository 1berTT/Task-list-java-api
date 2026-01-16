package br.com.humbertodamasceno.task_list.modules.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.humbertodamasceno.task_list.modules.admin.entities.AdminEntity;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {
    Optional<AdminEntity> findByEmail(String email);

    Optional<AdminEntity> findById(UUID id);

    Optional<AdminEntity> findByName(String name);
}
