package com.epam.esm.service.impl;

import com.epam.esm.data.User;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.UserDAO;
import com.epam.esm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAll(Long page, Long pageSize) {
        return userDAO.findAll(pageSize, page);
    }

    @Override
    public User findById(Long id) throws DAOException {
        return userDAO.findById(id).get();
    }
}
