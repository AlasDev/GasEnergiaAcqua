package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.entity.User;
import com.alas.gasenergiaacqua.filter.UserFilter;
import com.alas.gasenergiaacqua.mapper.UserMapper;
import com.alas.gasenergiaacqua.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * @param uuid the UUID of the User you want to get
     * @return a User if found
     * @throws NoSuchElementException if not found
     */
    public UserDTO getByUuid(UUID uuid) throws NoSuchElementException {
        return userMapper.mapToDto(userRepository.findByUuid(uuid).get());
    }

    /**
     * @param params filters applied to the search
     * @param pageable pageable
     * @return a pageDTO containing UserDTO
     */
    public PageDTO<UserSummaryDTO> searchBySpecification(UserFilter params, Pageable pageable) {
        return userMapper.mapToPageDTO(userRepository.findAll(params.toSpecification(), pageable));
    }

    /**
     * Deletes a User with given uuid
     * @param uuid uuid of the User which is going to be deleted
     * @return a ResponseMessage
     */
    public ResponseMessage deleteByUuid(UUID uuid) {
        userRepository.findByUuid(uuid).ifPresent(userRepository::delete);

        return ResponseMessage.builder()
                .message("User deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @param userDTO info needed to create a new user
     * @return a ResponseMessage
     */
    public ResponseMessage postNew(UserRegisterDTO userDTO) {
        String passwordHash = DigestUtils.sha256Hex(userDTO.getPassword());

        User newUser = User.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .password(passwordHash)
                .build();
        userRepository.save(newUser);

        return ResponseMessage.builder()
                .message("New user registered successfully")
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @param userDTO the changes you want to apply
     * @return the updated User
     */
    public UserDTO updateUser(UserUpdateDTO userDTO) {
        User user = userRepository.findByUuid(userDTO.getUuid()).get();

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
}
