package br.erik.apiauthmodel.dto;

import br.erik.apiauthmodel.entities.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
 ) {
}
