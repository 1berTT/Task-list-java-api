package br.com.humbertodamasceno.task_list.modules.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.humbertodamasceno.task_list.modules.user.entitites.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findById(UUID id);
}
