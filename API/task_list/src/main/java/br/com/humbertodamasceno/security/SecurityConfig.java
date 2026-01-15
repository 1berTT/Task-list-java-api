package br.com.humbertodamasceno.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityAdminFilter securityAdminFilter;

    @Autowired
    private SecurityUserFilter securityUserFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> {
            csrf.disable();
        }).authorizeHttpRequests((auth) -> {
            // Rotas públicas (sem autenticação)
            auth.requestMatchers("/admin/create", "/admin/login").permitAll();
            auth.requestMatchers("/user/create", "/user/login").permitAll();
            // Todas as outras rotas requerem autenticação
            auth.anyRequest().authenticated();
        })
                // Adiciona os filtros customizados antes do filtro de autenticação padrão
                // A ordem importa: SecurityAdminFilter primeiro, depois SecurityUserFilter
                .addFilterBefore(this.securityAdminFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(this.securityUserFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
