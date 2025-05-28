package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.auth.JwtTokenProvider;
import com.alas.gasenergiaacqua.dto.LoginSuccessResponseMessage;
import com.alas.gasenergiaacqua.dto.ResponseMessage;
import com.alas.gasenergiaacqua.dto.UserLoginDTO;
import com.alas.gasenergiaacqua.dto.UserRegisterDTO;
import com.alas.gasenergiaacqua.entity.User;
import com.alas.gasenergiaacqua.mapper.UserMapper;
import com.alas.gasenergiaacqua.repository.UserRepository;
import com.alas.gasenergiaacqua.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordUtil passwordUtil;

    public AuthService(UserRepository userRepository,
                       UserMapper userMapper,
                       UserService userService,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordUtil = passwordUtil;
    }

    /**
     * User is obtained using just the email instead of both "email and password" because Bcrypt doesn't let you hash
     * and get the same hashed result without using the same "salt",
     * if hashing a password and letting the repository try to match it with the hashed password that is already saved
     * in the database, they will not match. You need to obtain the user and use {@code PasswordUtil.match()} to properly
     * check if they match or not.
     *
     * @param credentials user credentials
     * @return a new token inside a JSON
     */
    public ResponseMessage login(UserLoginDTO credentials) {
        String email = credentials.getEmail();
        User userFound = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Cannot login!\nUser with inserted credentials was not found!"));
        boolean isMatched = passwordUtil.match(credentials.getPassword(), userFound.getPassword());

        if (!isMatched)
            throw new NoSuchElementException("Cannot login!\nUser with inserted credentials was not found!");

        String token = jwtTokenProvider.generateToken(userMapper.mapToDto(userFound));
        return LoginSuccessResponseMessage.builder()
                .timestamp(Instant.now())
                .message("Successfully logged in!\nUserType: " + userFound.getUserType())
                .token(token)
                .build();

    }

    /**
     * @param userRegisterDTO user info needed to register a new user
     */
    public ResponseMessage register(UserRegisterDTO userRegisterDTO) {
        return userService.postNew(userRegisterDTO);
    }
}
