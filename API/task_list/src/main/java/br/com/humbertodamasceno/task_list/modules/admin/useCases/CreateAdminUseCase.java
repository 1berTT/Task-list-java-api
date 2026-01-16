package br.com.humbertodamasceno.task_list.modules.admin.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.exceptions.AdminRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.admin.entities.AdminEntity;
import br.com.humbertodamasceno.task_list.modules.admin.repositories.AdminRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CreateAdminUseCase {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminEntity execute(AdminEntity adminEntity) {
        this.adminRepository.findByEmail(adminEntity.getEmail()).ifPresent((admin) -> {
            throw new AdminRuntimeExceptions("Email already exists");
        });

        this.adminRepository.findByName(adminEntity.getName()).ifPresent((admin) -> {
            throw new AdminRuntimeExceptions("Name already exists");
        });

        var password = this.passwordEncoder.encode(adminEntity.getPassword());
        adminEntity.setPassword(password);

        return this.adminRepository.save(adminEntity);
    }
}
