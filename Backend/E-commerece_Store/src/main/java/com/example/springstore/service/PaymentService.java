package com.example.springstore.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public String processPayment(String paymentMethod, BigDecimal amount) {
        Stripe.apiKey = stripeApiKey;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount.multiply(new BigDecimal(100)).longValue()) // in cents
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .build();

        try {
            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getId();
        } catch (StripeException e) {
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }
    }
}