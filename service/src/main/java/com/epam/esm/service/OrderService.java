package com.epam.esm.service;

import com.epam.esm.data.Order;
import com.epam.esm.service.data.CreateOrderDto;

import java.util.List;

public interface OrderService {
    List<Order> getAll(Long pageSize, Long page);

    List<Order> findAllUserOrders(Long pageSize, Long page, Long userId);

    Order createOrder(CreateOrderDto order) throws DAOException;

    void removeOrder(Long orderId) throws DAOException;

    void updateOrder(Order order, Long orderId);
}
