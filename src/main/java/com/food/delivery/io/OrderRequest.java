package com.food.delivery.io;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class OrderRequest {
    // user is is being taken from the SecurityContext holder vial userimpl
    private String userAddress;
    private List<OrderItem > orderItems;
    private Double amount;
    private String email ;
    private String phoneNumber ;
    private String orderStatus ;


}
