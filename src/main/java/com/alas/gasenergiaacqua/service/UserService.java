package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.auth.JwtTokenProvider;
import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.entity.User;
import com.alas.gasenergiaacqua.entity.UserType;
import com.alas.gasenergiaacqua.exception.ElementAlreadyPresentException;
import com.alas.gasenergiaacqua.filter.UserFilter;
import com.alas.gasenergiaacqua.mapper.UserMapper;
import com.alas.gasenergiaacqua.repository.UserRepository;
import com.alas.gasenergiaacqua.util.PasswordUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordUtil passwordUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordUtil passwordUtil,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordUtil = passwordUtil;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * @param id uuid of the User you want to get
     * @return a DTO if found
     */
    public UserDTO getById(UUID id){
        return userMapper.mapToDto(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo user was found with id: " + id)));
    }

    /**
     * @param params filters applied to the search
     * @param pageable pageable
     * @return a pageDTO containing a list of DTO
     */
    public PageDTO<UserSummaryDTO> searchBySpecification(Pageable pageable, UserFilter params) {
        return userMapper.mapToPageDTO(userRepository.findAll(params.toSpecification(), pageable));
    }

    /**
     * Deletes a User with given uuid
     * @param id uuid of the User which is going to be deleted
     * @return a ResponseMessage
     */
    public ResponseMessage deleteById(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot delete!\nNo user was found with id: " + id));
        userRepository.deleteById(id);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("User deleted successfully")
                .build();
    }

    /**
     * @param userDTO info needed to create a new user
     * @return a ResponseMessage
     */
    public ResponseMessage postNew(UserRegisterDTO userDTO) {
//        String passwordHash = DigestUtils.sha256Hex(userDTO.getPassword());
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ElementAlreadyPresentException("Cannot register a new user! Email " + userDTO.getEmail() + " is already in use");
        }

        String passwordHash = passwordUtil.hash(userDTO.getPassword());

        User newUser = User.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .password(passwordHash)
                .userType(UserType.builder().id(1).build()) //default for all new users
                .build();
        userRepository.save(newUser);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("New user registered successfully")
                .build();
    }

    /**
     * @param userDTO the changes you want to apply
     * @return the updated User
     */
    public UserDTO updateUser(UserUpdateDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo user was found"));

        if (userDTO.getName() != null && !userDTO.getName().trim().isEmpty()) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getSurname() != null && !userDTO.getSurname().trim().isEmpty()) {
            user.setSurname(userDTO.getSurname());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().trim().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
            String updatedPasswordHash = DigestUtils.sha256Hex(userDTO.getPassword());
            user.setPassword(updatedPasswordHash);
        }

        return userMapper.mapToDto(userRepository.save(user));
    }

    public UserDTO getUserFromToken(String token) {
        UUID id = jwtTokenProvider.extractIdFromClaims(token);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
        return userMapper.mapToDto(user);
    }
}
