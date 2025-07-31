package com.food.delivery.io;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class OrderResponse {
    private String id ;
    private String userId ;
    private String userAddress ;
    private String phoneNumber ;
    private String email ;

    private String paymentStatus ;
    private String razorpayId ;

    private String orderStatus ;
    // order items for admin panel
    private List<OrderItem> orderItems;
}


