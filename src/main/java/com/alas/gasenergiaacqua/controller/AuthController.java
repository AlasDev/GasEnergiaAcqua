package com.alas.gasenergiaacqua.controller;

import com.alas.gasenergiaacqua.auth.JwtTokenProvider;
import com.alas.gasenergiaacqua.dto.ResponseMessage;
import com.alas.gasenergiaacqua.dto.UserLoginDTO;
import com.alas.gasenergiaacqua.dto.UserRegisterDTO;
import com.alas.gasenergiaacqua.exception.AuthenticationException;
import com.alas.gasenergiaacqua.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @GetMapping("/tokenValidation")
    public boolean validateToken(@RequestHeader("Authorization") String jwtToken) {
        if (jwtTokenProvider.isTokenEmpty(jwtToken) || !jwtTokenProvider.isValid(jwtToken) || jwtTokenProvider.isTokenExpired(jwtToken)) {
            return false;
        }
        return true;
    }
}
