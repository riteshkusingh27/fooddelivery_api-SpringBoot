package com.food.delivery.controller;

import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;
import com.food.delivery.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/create")
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request)  {
        try {
            return orderService.createOrderWithPayment(request);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
}
