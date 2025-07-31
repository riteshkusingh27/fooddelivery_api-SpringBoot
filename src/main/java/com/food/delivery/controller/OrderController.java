package com.food.delivery.controller;

import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;
import com.food.delivery.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request)  {
        try {
            return orderService.createOrderWithPayment(request);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String , String > paymentData){
          orderService.verifyPayent(paymentData, "Paid");
    }

    @GetMapping
    public List<OrderResponse> getOrders(){
       return orderService.getUserOrders();
    }
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId)
    {
        orderService.removeOrder(orderId);
    }
    // admin
    @GetMapping("/all")
    public List<OrderResponse> getOrdersofAllUsers(){
        return orderService.getOrderOfAllUsers();
    }
        // admin
    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId , @RequestParam String status){
        orderService.updateOrderStatus(orderId,status);

    }
}
