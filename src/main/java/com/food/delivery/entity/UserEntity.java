package com.food.delivery.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="user")
@Builder
public class UserEntity {
    @Id
  private String id ;
    private String name ;
    private String email ;
    private String password ;
}
