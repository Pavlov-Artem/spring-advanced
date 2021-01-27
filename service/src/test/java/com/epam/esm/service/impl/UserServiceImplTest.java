package com.epam.esm.service.impl;

import com.epam.esm.data.User;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.UserDAO;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
//
//    @Mock
//    private UserDAO userDAO;
//
//    @InjectMocks
//    UserService userService = new UserServiceImpl(userRepository, userDAO);
//
//    @BeforeEach
//    void setup() {
//        userService = new UserServiceImpl(userRepository, userDAO);
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAll() {
//        Long page = 1L;
//        Long pageSize = 10L;
//        List<User> expectedUsers = new ArrayList<>();
//        for (int i = 0; i < 10 ; i++) {
//            expectedUsers.add(new User());
//        }
//        Mockito.when(userDAO.findAll(pageSize, page)).thenReturn(expectedUsers);
//        List<User> actualUsers = userService.getAll(page, pageSize);
//        Assertions.assertEquals(10, actualUsers.size());
//    }
//
//    @Test
//    void findById() throws DAOException {
//        User user = new User();
//        user.setId(1L);
//        user.setName("John Doe");
//        user.setEmail("johndoe@gmail.com");
//
//        Mockito.when(userDAO.findById(1L))
//                .thenReturn(Optional.of(user));
//        User actualUser = userService.findById(1L);
//        Assertions.assertEquals(user,actualUser);
//    }


}