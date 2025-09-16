package br.erik.apiauthmodel.security.userdetails;

import br.erik.apiauthmodel.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
         * Este metodo retorna uma coleção de objetos `GrantedAuthority`
         * que representam as permissões ou papéis do usuário autenticado.
         * Ele faz isso acessando a lista de papéis (`roles`) do usuário,
         * convertendo cada papel em um objeto `SimpleGrantedAuthority` (usando o nome do papel)
         * e coletando todos em uma lista.
         * Essa lista é usada pelo Spring Security para determinar o que o usuário pode acessar no sistema.
         */
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
