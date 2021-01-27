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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", params = {"page", "size"})
    public ResponseEntity<List<User>> getAll(@RequestParam("size") Long pageSize, @RequestParam("page") Long page) throws DAOException {
        List<User> users = userService.getAll(page, pageSize);
        for (User user : users) {
            addLinks(user);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) throws DAOException {
        User user = userService.getById(id);
        addLinks(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    private void addLinks(User user) throws DAOException {
        user.add(linkTo(methodOn(UserRestController.class).getById(user.getId())).withSelfRel());
    }
}
