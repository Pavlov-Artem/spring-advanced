package com.epam.esm.service;

import com.epam.esm.data.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAll(Long page, Long pageSize);

    User findById(Long id) throws DAOException;
}
