package com.food.delivery.io;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
      public String foodId;
      private int quantity;;
      private double price ;
      private String Category ;
      private String imageUrl ;
      private String description ;
      private String name ;
}
