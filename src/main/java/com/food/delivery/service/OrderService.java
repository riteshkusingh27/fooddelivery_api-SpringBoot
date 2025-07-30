package com.food.delivery.service;

import com.food.delivery.io.CartRequest;
import com.food.delivery.io.CartResponse;
import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request);
}
