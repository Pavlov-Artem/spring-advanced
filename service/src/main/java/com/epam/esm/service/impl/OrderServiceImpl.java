package com.epam.esm.service.impl;

import com.epam.esm.data.GiftCertificate;
import com.epam.esm.data.Order;
import com.epam.esm.data.OrderDetails;
import com.epam.esm.service.*;
import com.epam.esm.service.data.CreateOrderDto;
import com.epam.esm.service.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private GiftCertificateDAO giftCertificateDAO;

    public OrderServiceImpl(OrderDAO orderDAO, UserDAO userDAO, GiftCertificateDAO giftCertificateDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Override
    public List<Order> getAll(Long pageSize, Long page) {
        return orderDAO.findAll(pageSize, page);
    }

    @Override
    public List<Order> findAllUserOrders(Long pageSize, Long page, Long userId) {
        return orderDAO.findByUserId(1L,1L, userId);
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderDto order) throws DAOException {
        Order newOrder = createNewOrder();
        newOrder.setUser(userDAO.findById(order.getUserId()).get());
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (Long gcId : order.getCertificateIds()) {
            Optional<GiftCertificate> giftCertificate = giftCertificateDAO.findById(gcId);
            giftCertificate.ifPresent(giftCertificates::add);
        }
        for (GiftCertificate giftCertificate : giftCertificates) {
            newOrder.getOrderDetails().add(new OrderDetails(giftCertificate.getPrice(), giftCertificate, newOrder));
        }

        Long orderId = orderDAO.createEntity(newOrder);
        Order createdOrder = orderDAO.findById(orderId).get();
        return createdOrder;
    }

    @Override
    @Transactional
    public void removeOrder(Long orderId) throws DAOException {
        Optional<Order> order = orderDAO.findById(orderId);
        if (order.isPresent()) {
            orderDAO.deleteEntity(order.get());
        } else {
            throw new OrderNotFoundException(String.format("order can't be removed cause order with id=%s not found", orderId));
        }
    }

    @Override
    public void updateOrder(Order order, Long orderId) {

    }

    private Order createNewOrder() {
        Date currentDate = new Date();
        Timestamp currentDateTimestamp = new Timestamp(currentDate.getTime());

        Order order = new Order();
        order.setPurchaseDate(currentDateTimestamp);
        List<OrderDetails> orderDetails = new ArrayList<>();

        return order;
    }
}
