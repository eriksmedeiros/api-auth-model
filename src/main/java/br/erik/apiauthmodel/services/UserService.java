package br.erik.apiauthmodel.services;

import br.erik.apiauthmodel.dto.CreateUserDto;
import br.erik.apiauthmodel.dto.LoginUserDto;
import br.erik.apiauthmodel.dto.RecoveryJwtTokenDto;
import br.erik.apiauthmodel.entities.Role;
import br.erik.apiauthmodel.entities.User;
import br.erik.apiauthmodel.repositories.UserRepository;
import br.erik.apiauthmodel.security.auth.JwtTokenService;
import br.erik.apiauthmodel.security.config.SecurityConfiguration;
import br.erik.apiauthmodel.security.userdetails.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;

    public UserService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserRepository userRepository, SecurityConfiguration securityConfiguration) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
    }

    // Metodo responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {

        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().role(createUserDto.role()).build()))
                .build();

        userRepository.save(newUser);
    }

    // Metodo responsavel por autenticar um usuario e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticacao com o email e a senha do usuario
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }
}
