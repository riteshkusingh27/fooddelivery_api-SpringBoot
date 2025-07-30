package com.food.delivery.repository;

import com.food.delivery.entity.OrderEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Orderrepo extends MongoRepository<OrderEntity, String> {


    List<OrderEntity> findByUserId(String userId);

    Optional<OrderEntity> findByRazorpayId(String razorpayId);


}
