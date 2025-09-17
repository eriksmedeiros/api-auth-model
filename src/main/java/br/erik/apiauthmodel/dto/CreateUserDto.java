package br.erik.apiauthmodel.dto;

import br.erik.apiauthmodel.enums.RoleName;

public record CreateUserDto(
    String email,
    String password,
    RoleName role)
{}