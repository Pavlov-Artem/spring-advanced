package com.epam.esm.api.rest;

import com.epam.esm.api.AppConstants;
import com.epam.esm.data.UserOrder;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.data.CreateOrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrdersRestController {
    private final OrderService orderService;

    public OrdersRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders", params = {"page", "size"})
    public ResponseEntity<List<UserOrder>> getAll(@RequestParam(name = "size", required = false) Long pageSize,
                                                  @RequestParam(name = "page", required = false) Long page) throws DAOException {
        if (pageSize == null || page == null) {
            page = AppConstants.DEFAULT_PAGE;
            pageSize = AppConstants.DEFAULT_PAGE_SIZE;
        }
        List<UserOrder> userOrders = orderService.getAll(pageSize, page);
        for (UserOrder userOrder: userOrders) {
            addLinks(userOrder);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOrders);
    }

    @GetMapping(value = "/orders", params = {"user"})
    public ResponseEntity<List<UserOrder>> getAllUserOrders(@RequestParam("user") Long user) throws DAOException {
        List<UserOrder> userOrders = orderService.findAllUserOrders(1L,1L,user);
        for (UserOrder userOrder: userOrders) {
            addLinks(userOrder);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOrders);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<UserOrder> createOrder(@RequestBody CreateOrderDto createOrderDto) throws DAOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createOrderDto));
    }

    @DeleteMapping(value = "/orders/{id}")
    public ResponseEntity<String> removeOrder(@PathVariable("id") Long orderId) throws DAOException {
        orderService.removeOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("UserOrder with id=%s was removed", orderId));
    }

    private void addLinks(UserOrder userOrder) throws DAOException {
        userOrder.add(linkTo(methodOn(OrdersRestController.class).getAllUserOrders(1L)).withSelfRel());
        userOrder.add(linkTo(methodOn(OrdersRestController.class).createOrder(new CreateOrderDto())).withRel("create"));
        userOrder.add(linkTo(methodOn(OrdersRestController.class).removeOrder(1L)).withRel("remove"));

    }

}
