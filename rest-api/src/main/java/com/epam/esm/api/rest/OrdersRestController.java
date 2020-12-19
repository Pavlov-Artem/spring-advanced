package com.epam.esm.api.rest;

import com.epam.esm.data.Order;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.data.CreateOrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersRestController {
    private OrderService orderService;

    public OrdersRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders", params = {"page", "size"})
    public ResponseEntity<List<Order>> getAll(@RequestParam("size") Long pageSize, @RequestParam("page") Long page) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAll(pageSize, page));
    }

    @GetMapping(value = "/orders", params = {"user"})
    public ResponseEntity<List<Order>> getAllUserOrders(@RequestParam("user") Long user){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllUserOrders(1L,1L,user));
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDto createOrderDto) throws DAOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createOrderDto));
    }

    @DeleteMapping(value = "/orders/{id}")
    public ResponseEntity<String> removeOrder(@PathVariable("id") Long orderId) throws DAOException {
        orderService.removeOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Order with id=%s was removed", orderId));
    }

}
