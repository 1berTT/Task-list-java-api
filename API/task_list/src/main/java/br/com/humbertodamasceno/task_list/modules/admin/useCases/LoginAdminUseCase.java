package br.com.humbertodamasceno.task_list.modules.admin.useCases;

import java.time.Instant;
import java.util.Arrays;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.humbertodamasceno.task_list.exceptions.AdminRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.admin.DTOs.AdminLoginRequestDTO;
import br.com.humbertodamasceno.task_list.modules.admin.DTOs.AdminLoginResponseDTO;
import br.com.humbertodamasceno.task_list.modules.admin.repositories.AdminRepository;
import br.com.humbertodamasceno.task_list.providers.JWTAdminProvider;

@Service
public class LoginAdminUseCase {

    @Value("${security.token.secret.admin}")
    private String secretKey;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminLoginResponseDTO execute(AdminLoginRequestDTO adminLoginRequestDTO) {
        var admin = this.adminRepository.findByEmail(adminLoginRequestDTO.email()).orElseThrow(() -> {
            throw new AdminRuntimeExceptions("Admin not found");
        });

        var passwordsMatches = this.passwordEncoder.matches(adminLoginRequestDTO.password(), admin.getPassword());

        if (!passwordsMatches) {
            throw new AdminRuntimeExceptions("Invalid password");
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(1));
        var token = JWT.create()
                .withIssuer("task_list")
                .withSubject(admin.getId().toString())
                .withClaim("roles", Arrays.asList("ADMIN"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var adminLoginResponseDTO = new AdminLoginResponseDTO().builder().access_token(token)
                .expires_in(expiresIn.toEpochMilli()).build();
        return adminLoginResponseDTO;
    }

}
