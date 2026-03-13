package com.example.springstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springstore.dto.request.OrderRequest;
import com.example.springstore.dto.response.CartItemResponse;
import com.example.springstore.dto.response.CartResponse;
import com.example.springstore.dto.response.OrderItemResponse;
import com.example.springstore.dto.response.OrderResponse;
import com.example.springstore.exception.ResourceNotFoundException;
import com.example.springstore.model.OrderEntity;
import com.example.springstore.model.OrderItem;
import com.example.springstore.model.OrderStatus;
import com.example.springstore.model.Product;
import com.example.springstore.model.User;
import com.example.springstore.repository.OrderRepository;
import com.example.springstore.repository.ProductRepository;
import com.example.springstore.repository.UserRepository;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CartResponse cart = cartService.getCart(userId);

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        String paymentId = paymentService.processPayment(orderRequest.getPaymentMethod(), cart.getTotalPrice());

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalPrice());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setPaymentId(paymentId);
        order.setStatus(OrderStatus.PAID);

        for (CartItemResponse item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        }

        OrderEntity savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);

        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<OrderEntity> orders = orderRepository.findByUser(user);
        return orders.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long userId, Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }
        return mapToResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return mapToResponse(orderRepository.save(order));
    }

    private OrderResponse mapToResponse(OrderEntity order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus().toString());
        response.setTotalAmount(order.getTotalAmount());
        response.setShippingAddress(order.getShippingAddress());
        List<OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
            OrderItemResponse itemResp = new OrderItemResponse();
            itemResp.setProductName(item.getProduct().getName());
            itemResp.setQuantity(item.getQuantity());
            itemResp.setPrice(item.getPrice());
            return itemResp;
        }).collect(Collectors.toList());
        response.setItems(itemResponses);
        return response;
    }
}
