package com.example.springstore.dto.response;



import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CartResponse {
    private Long cartId;
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;
}