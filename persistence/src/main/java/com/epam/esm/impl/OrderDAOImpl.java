package com.epam.esm.impl;

import com.epam.esm.data.User;
import com.epam.esm.data.UserOrder;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.OrderDAO;
import com.epam.esm.service.exceptions.OrderCreationException;
import com.epam.esm.service.exceptions.UnsupportedOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Transactional
public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    private CommonJpaOperations<UserOrder> commonJpaOperations = new CommonJpaOperations<>();

    @Override
    public List<UserOrder> findAll(Long pageSize, Long page) {
        return Collections.unmodifiableList(commonJpaOperations.findAllBasic(pageSize, page, UserOrder.class, entityManager));
    }

    @Override
    @Transactional
    public Long createEntity(UserOrder entity) throws DAOException {
        try {
            this.entityManager.persist(entity);
            return (Long) this.entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
        } catch (Exception ex) {
            throw new OrderCreationException("error while creating order", entity);
        }
    }

    @Override
    public Optional<UserOrder> findById(Long id) throws DAOException {
        UserOrder userOrder = entityManager.find(UserOrder.class, id);
        return userOrder == null ? Optional.empty() : Optional.of(userOrder);
    }

    @Override
    public void updateEntity(UserOrder entity) throws DAOException {
        throw new UnsupportedOperationException("temporary unavailable", "update order");
    }

    @Override
    @Transactional
    public void deleteEntity(UserOrder entity) throws DAOException {
        try {
            UserOrder userOrder = entityManager.find(UserOrder.class, entity.getId());
            entityManager.remove(userOrder);
        } catch (Exception ex) {
            throw new DAOException(String.format("cannot remove order with id={0}", entity.getId()));
        }
    }

    @Override
    @Transactional
    public List<UserOrder> findByUserId(Long pageSize, Long page, Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserOrder> cq = cb.createQuery(UserOrder.class);
        Root<User> userRoot = cq.from(User.class);
        cq.where(cb.equal(userRoot.get("id"), userId));
        SetJoin<User, UserOrder> orders = userRoot.joinSet("userOrders");
        CriteriaQuery<UserOrder> query = cq.select(orders);
        return entityManager.createQuery(query).getResultList();
    }
}
