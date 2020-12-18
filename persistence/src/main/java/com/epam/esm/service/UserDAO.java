package com.epam.esm.service;

import com.epam.esm.data.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CRUDOperations<User> {

}
