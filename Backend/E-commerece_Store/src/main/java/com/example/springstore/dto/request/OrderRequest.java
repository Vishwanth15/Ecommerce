package com.example.springstore.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String shippingAddress;
    private String paymentMethod; // e.g., "STRIPE"
}