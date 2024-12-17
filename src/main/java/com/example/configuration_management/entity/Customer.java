package com.example.configuration_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    private int id;
    private String name;
    private String email;
    private String password;
    private String role;
}
