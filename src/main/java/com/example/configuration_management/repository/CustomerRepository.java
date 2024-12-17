package com.example.configuration_management.repository;

import com.example.configuration_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailOrName(String email, String name);
}
