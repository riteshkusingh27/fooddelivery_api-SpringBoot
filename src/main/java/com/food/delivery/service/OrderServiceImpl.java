package com.food.delivery.service;

import com.food.delivery.entity.OrderEntity;
import com.food.delivery.io.CartRequest;
import com.food.delivery.io.CartResponse;
import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;
import com.food.delivery.repository.Orderrepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Orderrepo orderrepo;


    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) {
        OrderEntity neworder = convertToEntity(request);
        neworder = orderrepo.save(neworder);



        // create razorpay payment order

    }

    private OrderEntity convertToEntity(OrderRequest request) {
        OrderEntity.builder()
                .userId(request.getUserId())
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderitemslist(request.getOrderItems())
                .build();

    }
}
