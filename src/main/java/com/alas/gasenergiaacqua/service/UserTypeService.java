package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.UserTypeDTO;
import com.alas.gasenergiaacqua.mapper.UserTypeMapper;
import com.alas.gasenergiaacqua.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserTypeService {

    private final UserTypeMapper userTypeMapper;
    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeMapper userTypeMapper, UserTypeRepository userTypeRepository) {
        this.userTypeMapper = userTypeMapper;
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * @param id uuid of the User Type you want to get
     * @return a DTO if found
     */
    public UserTypeDTO getById(Integer id) {
        return userTypeMapper.mapToDto(userTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo user type was found with id: " + id)));
    }

    /**
     * @return a list of DTO
     */
    public List<UserTypeDTO> getAll() {
        return userTypeMapper.mapToDtos(userTypeRepository.findAll());
    }
}
