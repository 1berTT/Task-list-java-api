package br.com.humbertodamasceno.task_list.modules.user.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoadUserDTO;
import br.com.humbertodamasceno.task_list.modules.user.entitites.UserEntity;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;

@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public LoadUserDTO execute(LoadUserDTO userRequest) {
        var user = userRepository.findById(UUID.fromString(userRequest.getId()));
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        user.get().setName(userRequest.getName());
        user.get().setEmail(userRequest.getEmail());

        userRepository.save(user.get());

        return new LoadUserDTO().builder().id(user.get().getId().toString()).name(user.get().getName())
                .email(user.get().getEmail()).build();

    }
}
