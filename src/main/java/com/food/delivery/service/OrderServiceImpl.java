package com.food.delivery.service;

import com.food.delivery.entity.OrderEntity;

import com.food.delivery.io.OrderRequest;
import com.food.delivery.io.OrderResponse;
import com.food.delivery.repository.CartRepo;
import com.food.delivery.repository.Orderrepo;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service

public class OrderServiceImpl implements OrderService {

    private final CartRepo cartRepo;

    private final Orderrepo orderrepo;
    private final UserService userService;
    @Value("${razorpay.id}")
    private String razorpayid;
    @Value("${razorpay.secret}")
    private String razorpaysecret;

    public OrderServiceImpl(CartRepo cartRepo, Orderrepo orderrepo, UserService userService) {
        this.cartRepo = cartRepo;
        this.orderrepo = orderrepo;
        this.userService = userService;
    }


    @Override
    @Transactional
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        OrderEntity neworder = convertToEntity(request);
        neworder = orderrepo.save(neworder);
        System.out.println(razorpayid);
        System.out.println(razorpaysecret);
        // create razorpay payment order
        RazorpayClient client = new RazorpayClient(razorpayid, razorpaysecret);
        // create json response to razorpay client
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", neworder.getAmount()*100);
        System.out.println(neworder.getAmount());
        orderRequest.put("currency", "INR");
        // order id created ;
        Order order = client.orders.create(orderRequest);
        neworder.setRazorpayId(order.get("id"));
        // even store the userid and save into the database
        String loggedinUser = userService.findByUserId();
        neworder.setUserId(loggedinUser);
        neworder = orderrepo.save(neworder);
        return convertToResponse(neworder);


    }

    @Override
    @Transactional    // since the db call are bundle into single transaction if one fails it rollbacks giving consistency
    public void verifyPayent(Map<String, String> paymentData, String status) {
        String razorpayOrderId = paymentData.get("razorpay_order_id");
        System.out.println(razorpayOrderId);
        OrderEntity existingOrder = orderrepo.findByRazorpayId(razorpayOrderId).orElseThrow(() -> new RuntimeException("Order not found "));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
        existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
        orderrepo.save(existingOrder);
                 System.out.println(paymentData);
           if("paid".equalsIgnoreCase(status)){

               //if the payment is succcessful will remove  the cart
               cartRepo.deleteByUserId(existingOrder.getUserId());

           }


    }

    @Override
    public List<OrderResponse> getUserOrders() {
        // get logged in userId
        String loggedUserId = userService.findByUserId();
      List<OrderEntity> list =   orderrepo.findByUserId(loggedUserId);
       return  list.stream().map(entity ->convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderid) {

        orderrepo.deleteById(orderid);

    }

    @Override
    public List<OrderResponse> getOrderOfAllUsers() {
        List<OrderEntity> listorder = orderrepo.findAll();
          return   listorder.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderid, String status) {
       OrderEntity entityy =  orderrepo.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
       entityy.setOrderStatus(status);
       orderrepo.save(entityy);
    }

    private OrderResponse convertToResponse(OrderEntity neworder) {
        return OrderResponse.builder()
                .id(neworder.getId())
                .userId(neworder.getUserId())
                .userAddress(neworder.getUserAddress())
                .paymentStatus(neworder.getPaymentStatus())
                .razorpayId(neworder.getRazorpayId())
                .paymentStatus(neworder.getPaymentStatus())
                .phoneNumber(neworder.getPhoneNumber())
                .email(neworder.getEmail())
                .orderItems(neworder.getOrderitemslist())
                .amount(String.valueOf(neworder.getAmount()))
                .orderStatus(neworder.getOrderStatus())

                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        return OrderEntity.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .orderitemslist(request.getOrderItems())
                .build();

    }
}
