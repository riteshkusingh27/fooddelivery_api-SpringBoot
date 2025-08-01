package com.food.delivery.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.io.FoodRequest;
import com.food.delivery.io.FoodResponse;
import com.food.delivery.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("*")   // allowed for every  frontend origin
public class FoodControler {

    private final FoodService foodService;

    public FoodControler(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString ,
                               @RequestPart("file") MultipartFile file  ){
        ObjectMapper objmapper = new ObjectMapper();
        FoodRequest request = null ;
        try {
             // taking request from the body and using object mapper converting the json data to Food request object
          request =   objmapper.readValue(foodString , FoodRequest.class);

        }
        catch(Exception e){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Invalid Json Format");

        }
       FoodResponse response =  foodService.addFood(request,file);
        return response;

    }
     ///  all exsting food from the db
@GetMapping
    public List<FoodResponse> readFoods(){
         return foodService.readFoods();
    }


     ///  food by id
   @GetMapping("/{id}")
    public FoodResponse readFood(@PathVariable String id){
    return foodService.readFood(id);

    }

     // delete the food from the database and from s3 bucket
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)   // 204 code no content
    public void deleteFood(@PathVariable String id){

       foodService.deleteFood(id);

    }

}
