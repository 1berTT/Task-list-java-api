package br.com.humbertodamasceno.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import br.com.humbertodamasceno.providers.JWTUserProvider;

@Component
public class SecurityUserFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUserProvider jwtUserProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();

        // Aplica o filtro apenas para rotas /user (exceto as públicas)
        if (servletPath.startsWith("/user") &&
                !servletPath.equals("/user/create") &&
                !servletPath.equals("/user/login")) {

            String header = request.getHeader("Authorization");

            if (header == null || header.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token de autenticação é obrigatório");
                return;
            }

            try {
                var token = this.jwtUserProvider.validateToken(header);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido");
                    return;
                }

                request.setAttribute("user_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        token.getSubject(), null, grants);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
