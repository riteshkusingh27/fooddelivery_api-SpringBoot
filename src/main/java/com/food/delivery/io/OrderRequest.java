package com.food.delivery.io;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class OrderRequest {
    private String userId;
    private String userAddress;
    private List<OrderItem > orderItems;
    private Double amount;


}
