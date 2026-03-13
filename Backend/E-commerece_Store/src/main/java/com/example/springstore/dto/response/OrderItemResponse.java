package com.example.springstore.dto.response;



import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
