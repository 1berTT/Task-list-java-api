package br.com.humbertodamasceno.task_list.modules.user.useCases;

import java.time.Instant;
import java.time.Duration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoginUserRequestDTO;
import br.com.humbertodamasceno.task_list.modules.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.humbertodamasceno.task_list.exceptions.UserRuntimeExceptions;
import br.com.humbertodamasceno.task_list.modules.user.DTOs.LoginUserResponseDTO;

@Service
public class LoginUserUseCase {

    @Value("${security.token.secret.user}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginUserResponseDTO execute(LoginUserRequestDTO loginUserRequestDTO) {
        var user = this.userRepository.findByEmail(loginUserRequestDTO.email()).orElseThrow(() -> {
            throw new UserRuntimeExceptions("User not found with email: " + loginUserRequestDTO.email());
        });

        var passwordMatches = this.passwordEncoder.matches(loginUserRequestDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new UserRuntimeExceptions("Invalid password");
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(1));

        var token = JWT.create()
                .withIssuer("task_list")
                .withSubject(user.getId().toString())
                .withClaim("roles", Arrays.asList("USER"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var loginUserResponseDTO = new LoginUserResponseDTO().builder().access_token(token)
                .expires_in(expiresIn.toEpochMilli()).build();

        return loginUserResponseDTO;
    }
}
