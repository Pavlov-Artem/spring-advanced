package com.epam.esm.impl;

import com.epam.esm.data.User;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.UserDAO;
import com.epam.esm.service.exceptions.UnsupportedOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@Primary
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private final CommonJpaOperations<User> commonJpaOperations = new CommonJpaOperations<>();
     //= new CommonJpaOperations<>();
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(Long pageSize, Long page) {
        return commonJpaOperations.findAllBasic(pageSize, page, User.class, entityManager);
    }

    /**
     * Temporary unsupported operation
     *
     * @param entity
     * @return
     * @throws DAOException
     */
    @Override
    public Long createEntity(User entity) throws DAOException {
        throw new UnsupportedOperationException("operation unavailable", "create user");
    }

    @Override
    public Optional<User> findById(Long id) throws DAOException {
        User user = entityManager.find(User.class, id);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    /**
     * Temporary unsupported operation
     *
     * @param entity
     * @throws DAOException
     */
    @Override
    public void updateEntity(User entity) throws DAOException {
        throw new UnsupportedOperationException("operation unavailable", "update user");
    }

    /**
     * Temporary unsupported operation
     *
     * @param entity
     * @throws DAOException
     */
    @Override
    public void deleteEntity(User entity) throws DAOException {
        throw new UnsupportedOperationException("operation unavailable", "delete user");
    }
}
