package com.example.configuration_management.controller;

import com.example.configuration_management.DTO.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@EnableMethodSecurity
@EnableConfigurationProperties(value = Application.class)
@Controller
@RequestMapping("api")
public class ControllerTest {
    @Value("${app.version}")
    private String appVersion;
    private final Application application;

    public ControllerTest(Application application) {
        this.application = application;
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("message")
    @ResponseBody
    public String message() {
        return "Hello World!";
    }

    @GetMapping("index")
    public String welcome() {
        return "index";
    }

    @PostAuthorize("hasAuthority('admin')")
    @GetMapping("properties")
    @ResponseBody
    public Application getProperties() {
        System.out.println(Arrays.toString(application.contact()));
        return application;
    }

    @GetMapping("version")
    @ResponseBody
    public String getVersion() {
        return appVersion;
    }

    @PostFilter("filterObject.firstName() != 'test' ")
    @PostMapping("contact")
    @ResponseBody
    public List<Contact> getContacts(@RequestBody List<Contact> contacts) {
        System.out.println("Inside getContacts()");
        System.out.println(contacts);
        return contacts;
    }

    @PreFilter("filterObject.firstName() != 'test' ")
    @PostMapping("contact2")
    @ResponseBody
    public Contact getContact(@RequestBody List<Contact> contacts) {
        System.out.println("Inside getContact()");
        System.out.println(contacts);
        return contacts.get(0);
    }

}
