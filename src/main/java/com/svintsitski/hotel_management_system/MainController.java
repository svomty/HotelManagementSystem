package com.svintsitski.hotel_management_system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MainController {
    // Обычно я использую интерфейс Model, но в целом разницы нет,
    // т.к. используется реализация LinkedHashMap(Key, Val)
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "index";
    }
    @RequestMapping("/greeting")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }
}