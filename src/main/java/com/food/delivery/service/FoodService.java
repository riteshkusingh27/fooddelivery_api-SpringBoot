package com.food.delivery.service;

import com.food.delivery.io.FoodRequest;
import com.food.delivery.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    // endpoints for the admin panel

    String uploadFile(MultipartFile file);

    FoodResponse addFood(FoodRequest request, MultipartFile file);

    List<FoodResponse> readFoods();

    FoodResponse readFood(String id);

    // delete food image from s3 bucket
    boolean deleteFilename(String filename);

    void deleteFood(String id);


}
