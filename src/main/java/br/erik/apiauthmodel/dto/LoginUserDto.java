package br.erik.apiauthmodel.dto;

public record LoginUserDto(
    String email,
    String password
) {
}
