package br.com.humbertodamasceno.task_list.modules.admin.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.admin.DTOs.LoadAdminDTO;
import br.com.humbertodamasceno.task_list.modules.admin.repositories.AdminRepository;
import br.com.humbertodamasceno.task_list.exceptions.AdminRuntimeExceptions;

@Service
public class LoadAdminUseCase {

    @Autowired
    private AdminRepository adminRepository;

    public LoadAdminDTO execute(UUID adminId) {
        var admin = this.adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminRuntimeExceptions("Admin not found"));

        return LoadAdminDTO.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .build();

    }
}
