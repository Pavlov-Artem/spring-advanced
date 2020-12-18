package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

/**
 * basics crud operations
 * @param <T> entity type
 */
public interface CRUDOperations<T> {
    List<T> findAll(Long pageSize, Long page);
    Long createEntity(T entity) throws DAOException;
    Optional<T> findById(Long id) throws DAOException;
    void updateEntity(T entity) throws DAOException;
    void deleteEntity(T entity) throws DAOException;

}
