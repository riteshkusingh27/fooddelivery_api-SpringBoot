package com.food.delivery.entity;

import com.food.delivery.io.OrderItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection="order")
@Data
@Builder
public class OrderEntity {
    @Id
    private String id ;
    private String userId ;
    private String userAddress ;
    private
    String phoneNumber ;
    private String email ;
    private List<OrderItem> orderitemslist ;
    private double amount ;
    private String paymentStatus ;
    private String razorpayId ;
    private String razorpaySignature;
    private String orderStatus ;



}
