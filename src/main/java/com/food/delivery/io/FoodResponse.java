package com.food.delivery.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FoodResponse {
    private String id ;
    private String imageUrl ;
    // food name
    private String name ;
    // food description
    private String description;
    // food price
    private Double price ;
    //food category
    private String category ;
}
