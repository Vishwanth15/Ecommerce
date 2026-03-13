package com.example.springstore.dto.response;



import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price; // unit price
    private BigDecimal totalPrice; // quantity * price
}
