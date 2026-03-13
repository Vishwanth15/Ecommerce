package com.example.springstore.controller;

import com.example.springstore.dto.request.CartItemRequest;
import com.example.springstore.dto.response.CartResponse;
import com.example.springstore.dto.response.MessageResponse;
import com.example.springstore.security.UserDetailsImpl;
import com.example.springstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        CartResponse cart = cartService.getCart(currentUser.getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItemToCart(@AuthenticationPrincipal UserDetailsImpl currentUser,
                                                       @RequestBody CartItemRequest itemRequest) {
        CartResponse updatedCart = cartService.addItemToCart(currentUser.getId(), itemRequest);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponse> updateCartItem(@AuthenticationPrincipal UserDetailsImpl currentUser,
                                                        @PathVariable Long productId,
                                                        @RequestBody CartItemRequest itemRequest) {
        CartResponse updatedCart = cartService.updateCartItem(currentUser.getId(), productId, itemRequest);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<MessageResponse> removeItemFromCart(@AuthenticationPrincipal UserDetailsImpl currentUser,
                                                               @PathVariable Long productId) {
        cartService.removeItemFromCart(currentUser.getId(), productId);
        return ResponseEntity.ok(new MessageResponse("Item removed from cart"));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> clearCart(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        cartService.clearCart(currentUser.getId());
        return ResponseEntity.ok(new MessageResponse("Cart cleared"));
    }
}
