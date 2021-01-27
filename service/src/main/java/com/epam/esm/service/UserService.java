package com.epam.esm.service;

import com.epam.esm.data.User;
import com.epam.esm.service.data.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAll(Long page, Long pageSize);
    UserDto findById(Long id) throws DAOException;
    User getById(Long id);
    User getByEmail(String email);
}
