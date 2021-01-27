package com.epam.esm.service.impl;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.UserOrder;
import com.epam.esm.service.*;
import com.epam.esm.service.data.CreateOrderDto;
import com.epam.esm.service.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private GifCertificateDAOAdv giftCertificateDAO;

    public OrderServiceImpl(OrderDAO orderDAO, UserDAO userDAO, GifCertificateDAOAdv giftCertificateDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Override
    public List<UserOrder> getAll(Long pageSize, Long page) {
        return orderDAO.findAll(pageSize, page);
    }

    @Override
    public UserOrder findById(Long id) throws DAOException {
        return orderDAO.findById(id).get();
    }

    @Override
    public List<UserOrder> findAllUserOrders(Long pageSize, Long page, Long userId) {
        return orderDAO.findByUserId(1L,1L, userId);
    }

    @Override
    @Transactional
    public UserOrder createOrder(CreateOrderDto order) throws DAOException {
        UserOrder newUserOrder = createNewOrder();
        newUserOrder.setUser(userDAO.findById(order.getUserId()).get());
        Optional<GiftCertificate> giftCertificate = giftCertificateDAO.findById(order.getCertificateId());
        newUserOrder.setCost(giftCertificate.get().getPrice());
        newUserOrder.setGiftCertificate(giftCertificate.get());
//
        Long orderId = orderDAO.createEntity(newUserOrder);
        return orderDAO.findById(orderId).get();
    }

    @Override
    @Transactional
    public void removeOrder(Long orderId) throws DAOException {
        Optional<UserOrder> order = orderDAO.findById(orderId);
        if (order.isPresent()) {
            orderDAO.deleteEntity(order.get());
        } else {
            throw new OrderNotFoundException(String.format("order can't be removed cause order with id=%s not found", orderId));
        }
    }

    @Override
    public void updateOrder(UserOrder userOrder, Long orderId) {

    }

    private UserOrder createNewOrder() {
        Date currentDate = new Date();
        Timestamp currentDateTimestamp = new Timestamp(currentDate.getTime());

        UserOrder userOrder = new UserOrder();
        userOrder.setPurchaseDate(currentDateTimestamp);

        return userOrder;
    }
}
