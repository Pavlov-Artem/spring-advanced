package com.epam.esm.service.impl;

import com.epam.esm.data.User;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.UserDAO;
import com.epam.esm.service.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.data.UserDto;
import com.epam.esm.service.exceptions.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserDAO userDAO;

    public UserServiceImpl(UserRepository userRepository, UserDAO userDAO) {
        this.userRepository = userRepository;
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAll(Long page, Long pageSize) {
        return userDAO.findAll(pageSize, page);
    }

    @Override
    public UserDto findById(Long id) throws DAOException {

        User user = userDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found", id));
        return UserDto.buildFromUser(user);
    }

    @Override
    public User getById(Long id) {

        return  userRepository.getById(id).orElseThrow(() -> new EntityNotFoundException("user not found", id)) ;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("user not found", 0L));
    }
}
