package com.svintsitski.hotel_management_system;

import com.svintsitski.hotel_management_system.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ServingWebContentApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServingWebContentApplication.class);
    final static String DOMAIN = "localhost";
    final static String PORT = "8184";
    final static String DOMAIN_FULL = "http://"+DOMAIN+":"+PORT+"/";

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
        LOGGER.info(DOMAIN_FULL);
    }

}