package com.svintsitski.hotel_management_system;

import com.svintsitski.hotel_management_system.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServingWebContentApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
        LOGGER.info("http://localhost:8184/");
    }

}