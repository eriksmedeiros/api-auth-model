package br.erik.apiauthmodel.security.auth;

import br.erik.apiauthmodel.entities.User;
import br.erik.apiauthmodel.repositories.UserRepository;
import br.erik.apiauthmodel.security.config.SecurityConfiguration;
import br.erik.apiauthmodel.security.userdetails.UserDetailsImpl;
import br.erik.apiauthmodel.security.userdetails.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { // Verifica se o endpoint requer autenticação antes de processar a requisição

        String token = recoveryToken(request); // Recupera o token do cabeçalho Authorization da requisição
        if (token != null) {
            String subject = jwtTokenService.getSubjectFromToken(token); // Obtém o assunto (neste caso, o nome de usuário) do token

            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(subject); // Cria um UserDetails com o usuário encontrado

            // Cria um objeto de autenticação do Spring Security
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), null, userDetails.getAuthorities());

            // Define o objeto de autenticação no contexto de segurança do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response); // Continua o processamento da requisição
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (validateTokenHeader(authHeader)) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    private boolean validateTokenHeader(String authHeader){
        return authHeader != null && authHeader.startsWith("Bearer ");
    }
}
