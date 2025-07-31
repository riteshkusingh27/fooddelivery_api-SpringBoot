package com.food.delivery.service;

import com.food.delivery.io.CartRequest;
import com.food.delivery.io.CartResponse;
import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;
import com.razorpay.RazorpayException;

import java.util.Map;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException;

    void verifyPayent(Map<String,String> paymentData , String status );
}
