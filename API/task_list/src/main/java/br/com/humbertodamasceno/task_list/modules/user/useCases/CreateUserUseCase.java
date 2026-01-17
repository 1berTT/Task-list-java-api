package br.com.humbertodamasceno.task_list.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.admin.repositories.AdminRepository;
import br.com.humbertodamasceno.task_list.modules.user.entitites.UserEntity;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UserEntity userEntity) {

        this.adminRepository.findByEmail(userEntity.getEmail()).ifPresent((admin) -> {
            throw new UserRuntimeExceptions("Admin email already exists");
        });

        this.adminRepository.findByName(userEntity.getName()).ifPresent((admin) -> {
            throw new UserRuntimeExceptions("Admin name already exists");
        });

        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent((user) -> {
            throw new UserRuntimeExceptions("User email already exists");
        });

        this.userRepository.findByName(userEntity.getName()).ifPresent((user) -> {
            throw new UserRuntimeExceptions("User name already exists");
        });

        var passwordEncoder = this.passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncoder);

        return this.userRepository.save(userEntity);

    }

}
