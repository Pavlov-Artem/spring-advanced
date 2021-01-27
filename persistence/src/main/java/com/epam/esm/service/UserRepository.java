package com.epam.esm.service;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getById(Long id);
    Optional<User> findUserByEmail(String email);
}
