package com.food.delivery.service;


import com.food.delivery.entity.CartEntity;
import com.food.delivery.io.CartRequest;
import com.food.delivery.io.CartResponse;
import com.food.delivery.repository.CartRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final UserService userService;

    //    get  userid via security context holder of logged user and fetch user id implementation from  userSErvice
    @Override

    public CartResponse addToCart(CartRequest request) {
        String LoggedUserId = userService.findByUserId();

        Optional<CartEntity> cart = cartRepo.findByUserId(LoggedUserId);
        // if cart is not available create a new car
        CartEntity entity = cart.orElseGet(() -> new CartEntity(LoggedUserId, new HashMap<>()));

        Map<String, Integer> cartitems = entity.getItems();
        cartitems.put(request.getFoodId(), cartitems.getOrDefault(request.getFoodId(), 0) + 1);
        entity.setItems(cartitems);


        entity = cartRepo.save(entity);

        return convertToRespones(entity);


    }

    @Override
    public CartResponse getCart() {
        // get the logged user
        String LoggedUserId = userService.findByUserId();
        CartEntity carten = cartRepo.findByUserId(LoggedUserId).orElse(new CartEntity(null, LoggedUserId, new HashMap<>()));
        // convert to response and returen it
        return convertToRespones(carten);

    }

    @Override
    public void clearCart() {
        String LoggedUserId = userService.findByUserId();
        cartRepo.deleteByUserId(LoggedUserId);

    }

    @Override
    public CartResponse removeFromCart(CartRequest cartRequest) {
        String LoggedUserId = userService.findByUserId();
        CartEntity userCart = cartRepo.findByUserId(LoggedUserId).orElseThrow(() -> new RuntimeException("cart is not found"));
        ///  get the items
        Map<String, Integer> carItems = userCart.getItems();
        if (carItems.containsKey(cartRequest.getFoodId())) {
            int currentQty = carItems.get(cartRequest.getFoodId());
            if (currentQty > 0) {
                carItems.put(cartRequest.getFoodId(), --currentQty);

            } else {
                // already 0 remove that
                carItems.remove(cartRequest.getFoodId());

            }
            // save to databsae
            userCart = cartRepo.save(userCart);


        }
        return convertToRespones(userCart);

    }

    private CartResponse convertToRespones(CartEntity en) {
        return CartResponse.builder()
                .id(en.getId())
                .userId(en.getUserId())
                .items(en.getItems())
                .build();
    }
}
