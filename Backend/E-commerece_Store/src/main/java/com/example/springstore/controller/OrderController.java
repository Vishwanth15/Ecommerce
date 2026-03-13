package com.example.springstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springstore.dto.request.OrderRequest;
import com.example.springstore.dto.response.OrderResponse;
import com.example.springstore.security.UserDetailsImpl;
import com.example.springstore.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@AuthenticationPrincipal UserDetailsImpl currentUser,
        @RequestBody OrderRequest orderRequest) {
        OrderResponse order = orderService.createOrder(currentUser.getId(), orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        List<OrderResponse> orders = orderService.getUserOrders(currentUser.getId());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@AuthenticationPrincipal UserDetailsImpl currentUser,
        @PathVariable Long orderId) {
        OrderResponse order = orderService.getOrderById(currentUser.getId(), orderId);
        return ResponseEntity.ok(order);
    }
}