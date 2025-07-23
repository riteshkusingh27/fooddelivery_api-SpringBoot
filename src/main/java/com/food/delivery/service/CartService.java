package com.food.delivery.service;

import com.food.delivery.io.CartRequest;
import com.food.delivery.io.CartResponse;

public interface CartService {

   CartResponse addToCart(CartRequest request);

   CartResponse getCart();

   void clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);
}
