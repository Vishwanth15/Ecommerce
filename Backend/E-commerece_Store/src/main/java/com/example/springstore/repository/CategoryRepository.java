package com.example.springstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}