package com.example.configuration_management.controller;

import com.example.configuration_management.config.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

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

    @GetMapping("message")
    @ResponseBody
    public String message() {
        return "Hello World!";
    }

    @GetMapping("index")
    public String welcome() {
        return "index";
    }

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

}
