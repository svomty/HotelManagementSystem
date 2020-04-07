package com.svintsitski.hotel_management_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServingWebContentApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServingWebContentApplication.class);
    public final static String DOMAIN = "localhost";
    public final static String PORT = "8184";
    public final static String DOMAIN_FULL = "http://" + DOMAIN + ":" + PORT + "/";

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
        LOGGER.info(DOMAIN_FULL);
    }

}