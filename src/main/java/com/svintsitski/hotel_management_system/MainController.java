package com.svintsitski.hotel_management_system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MainController {
    // внедряем значение из application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";

    // Обычно я использую интерфейс Model, но в целом разницы нет,
    // т.к. используется реализация LinkedHashMap(Key, Val)
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "index";
    }
    @RequestMapping("/greeting")
    public String greeting(Map<String, Object> model) {
        model.put("message", this.message);
        return "greeting";
    }
}