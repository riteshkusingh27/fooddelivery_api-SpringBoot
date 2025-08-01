package com.food.delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "food")
public class FoodEnity {
    @Id
    private String id;  // in mongodb id is a  string as autogenerated
    private String name ;
    // food description
    private String description;
    // food price
    private Double price ;
    //food category
    private String category ;
    // food image url
    private String imageUrl ;
}
