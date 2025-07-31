package com.food.delivery.service;

import com.food.delivery.entity.OrderEntity;

import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;
import com.food.delivery.repository.Orderrepo;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service

public class OrderServiceImpl implements OrderService {

    private final Orderrepo orderrepo;
    private final UserService userService;
    @Value("${razorpay.id}")
    private String razorpayid;
    @Value("${razorpay.secret}")
    private String razorpaysecret;

    public OrderServiceImpl(Orderrepo orderrepo, UserService userService) {
        this.orderrepo = orderrepo;
        this.userService = userService;
    }


    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        OrderEntity neworder = convertToEntity(request);
        neworder = orderrepo.save(neworder);

           System.out.println(razorpayid);
           System.out.println(razorpaysecret);
        // create razorpay payment order
        RazorpayClient client = new RazorpayClient(razorpayid, razorpaysecret);
        // create json response to razorpay client

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", neworder.getAmount());
        orderRequest.put("currency", "INR");
        // order id created ;
        Order order = client.orders.create(orderRequest);
        neworder.setRazorpayId(order.get("id"));
        // even store the userid and save into the database
        String loggedinUser = userService.findByUserId();
        neworder.setUserId(loggedinUser);
       neworder =  orderrepo.save(neworder);
       return  convertToResponse(neworder);


    }

    @Override
    public void verifyPayent(Map<String, String> paymentData, String status) {

    }

    private OrderResponse convertToResponse(OrderEntity neworder) {
      return  OrderResponse.builder()
                .id(neworder.getId())
                .userId(neworder.getUserId())
                .userAddress(neworder.getUserAddress())
                .paymentStatus(neworder.getPaymentStatus())
                .razorpayId(neworder.getRazorpayId())
                .paymentStatus(neworder.getPaymentStatus())
                .phoneNumber(neworder.getPhoneNumber())
                .email(neworder.getEmail())

                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
      return  OrderEntity.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .orderitemslist(request.getOrderItems())
                .build();

    }
}
