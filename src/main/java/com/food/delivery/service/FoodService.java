package com.food.delivery.service;

import com.food.delivery.io.FoodRequest;
import com.food.delivery.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    String uploadFile(MultipartFile file);

  FoodResponse addFood(FoodRequest request , MultipartFile file);

  List<FoodResponse>  readFoods();
}
