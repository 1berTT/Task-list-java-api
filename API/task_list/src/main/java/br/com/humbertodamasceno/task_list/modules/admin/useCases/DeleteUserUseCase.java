package br.com.humbertodamasceno.task_list.modules.admin.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;

@Service
public class DeleteUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void execute(UUID userId) {
        var user = this.userRepository.findById(userId).orElseThrow(() -> new UserRuntimeExceptions("User not found"));

        this.userRepository.delete(user);
    }
}
