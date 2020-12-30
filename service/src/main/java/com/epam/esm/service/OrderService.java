package com.epam.esm.service;

import com.epam.esm.data.UserOrder;
import com.epam.esm.service.data.CreateOrderDto;

import java.util.List;

public interface OrderService {
    List<UserOrder> getAll(Long pageSize, Long page);

    UserOrder findById(Long id) throws DAOException;

    List<UserOrder> findAllUserOrders(Long pageSize, Long page, Long userId);

    UserOrder createOrder(CreateOrderDto order) throws DAOException;

    void removeOrder(Long orderId) throws DAOException;

    void updateOrder(UserOrder userOrder, Long orderId);
}
