package com.example.springstore.controller;

import com.example.springstore.dto.request.ProductRequest;
import com.example.springstore.dto.response.MessageResponse;
import com.example.springstore.dto.response.OrderResponse;
import com.example.springstore.dto.response.ProductResponse;
import com.example.springstore.service.OrderService;
import com.example.springstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/products")
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping("/products/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/products/{id}")
    public MessageResponse deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new MessageResponse("Product deleted");
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/orders/{orderId}/status")
    public OrderResponse updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}