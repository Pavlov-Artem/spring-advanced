package com.epam.esm.api.rest;

import com.epam.esm.data.User;
import com.epam.esm.service.DAOException;

import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", params = {"page", "size"})
    public ResponseEntity<List<User>> getAll(@RequestParam("size") Long pageSize, @RequestParam("page") Long page){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(page, pageSize));
    }
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) throws DAOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }
}
