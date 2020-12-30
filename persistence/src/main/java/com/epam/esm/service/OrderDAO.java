package com.epam.esm.service;

import com.epam.esm.data.UserOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends CRUDOperations<UserOrder> {
    List<UserOrder> findByUserId(Long pageSize, Long page, Long userId);
}
