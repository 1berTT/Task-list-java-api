package br.com.humbertodamasceno.task_list.modules.admin.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.admin.DTOs.LoadAdminDTO;
import br.com.humbertodamasceno.task_list.modules.admin.repositories.AdminRepository;

import br.com.humbertodamasceno.task_list.exceptions.AdminRuntimeExceptions;

@Service
public class UpdateAdminUseCase {

    @Autowired
    private AdminRepository adminRepository;

    public LoadAdminDTO execute(LoadAdminDTO loadAdminDTO) {

        var admin = this.adminRepository.findById(loadAdminDTO.getId()).orElseThrow(() -> {
            return new AdminRuntimeExceptions("Admin not found");
        });

        admin.setName(loadAdminDTO.getName());
        admin.setEmail(loadAdminDTO.getEmail());

        this.adminRepository.save(admin);

        return LoadAdminDTO.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .build();

    }

}
