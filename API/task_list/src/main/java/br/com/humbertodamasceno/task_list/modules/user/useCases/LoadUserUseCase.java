package br.com.humbertodamasceno.task_list.modules.user.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;
import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoadUserDTO;

@Service
public class LoadUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public LoadUserDTO execute(UUID userId) {
        var user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new UserRuntimeExceptions("User not found");
        });

        var loadUserDTO = new LoadUserDTO().builder().id(user.getId().toString()).name(user.getName())
                .email(user.getEmail()).build();

        return loadUserDTO;
    }

}
