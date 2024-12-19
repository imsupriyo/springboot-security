package com.example.configuration_management.DTO;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties(prefix = "app")
public record Application(String name, String[] contact, HashMap<String, String> map) {
}