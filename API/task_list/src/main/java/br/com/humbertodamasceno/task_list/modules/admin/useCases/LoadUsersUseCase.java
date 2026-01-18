package br.com.humbertodamasceno.task_list.modules.admin.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;
import java.util.List;
import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoadUserDTO;
import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import java.util.stream.Collectors;

@Service
public class LoadUsersUseCase {

    @Autowired
    private UserRepository userRepository;

    public List<LoadUserDTO> execute() {
        var users = this.userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserRuntimeExceptions("No users found");
        }
        return users.stream().map((user) -> {
            return new LoadUserDTO().builder().id(user.getId().toString()).name(user.getName()).email(user.getEmail())
                    .build();
        }).collect(Collectors.toList());
    }
}
