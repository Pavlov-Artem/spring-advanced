package com.epam.esm.service;

import com.epam.esm.data.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends CRUDOperations<Order> {
    List<Order> findByUserId(Long pageSize, Long page, Long userId);
}
