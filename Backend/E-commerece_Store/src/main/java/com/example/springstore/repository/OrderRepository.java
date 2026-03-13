package com.example.springstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstore.model.OrderEntity;
import com.example.springstore.model.User;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser(User user);
}