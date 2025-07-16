package com.food.delivery.io;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {

    // food name
    private String name ;
    // food description
    private String description;
    // food price
    private Double price ;
    //food category
        private String category ;
}
