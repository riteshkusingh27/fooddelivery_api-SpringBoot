package com.food.delivery.repository;

import com.food.delivery.entity.FoodEnity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo  extends MongoRepository<FoodEnity,String> {

}
