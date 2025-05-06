package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.dto.ResponseMessage;
import com.alas.gasenergiaacqua.dto.UserLoginDTO;
import com.alas.gasenergiaacqua.dto.UserRegisterDTO;
import com.alas.gasenergiaacqua.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * @param loginCredentials user info needed to autenticate
     * @return a message containing the JWT token
     */
    @PostMapping("/login")
    public ResponseMessage login(@Validated @RequestBody UserLoginDTO loginCredentials) {
        return authService.login(loginCredentials);
    }

    /**
     * @param registerCredentials info needed to create a new user
     * @return a message
     */
    @PostMapping("/register")
    public ResponseMessage register(@Validated @RequestBody UserRegisterDTO registerCredentials) {
        return authService.register(registerCredentials);
    }
}
