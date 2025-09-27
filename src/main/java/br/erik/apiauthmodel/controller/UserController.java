package br.erik.apiauthmodel.controller;

import br.erik.apiauthmodel.dto.CreateUserDto;
import br.erik.apiauthmodel.dto.LoginUserDto;
import br.erik.apiauthmodel.dto.RecoveryJwtTokenDto;
import br.erik.apiauthmodel.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Endpoints for user authentication and registration")
@SecurityRequirement(name = "SECURITY")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate User", description = "Authenticate a user and return a JWT token")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Register a new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid user data")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<Void> register(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    @Operation(summary = "Test Authentication Required", description = "Test endpoint to verify user authentication is required")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Usuário autenticado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    @Operation(summary = "Test Authentication Customer", description = "Test endpoint to verify customer authentication is required")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/test/administrador")
    @Operation(summary = "Test Authentication Admin", description = "Test endpoint to verify admin authentication is required")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso!", HttpStatus.OK);
    }



}
