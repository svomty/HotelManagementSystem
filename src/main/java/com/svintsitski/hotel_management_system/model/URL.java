package com.svintsitski.hotel_management_system.model;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

public class URL {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServingWebContentApplication.class);

    public static void IPInfo(String url, String ip, RequestMethod requestMethod) {
        LOGGER.info(requestMethod + " [" + ip + "] requested " + generateURL(url));
    }

    private static String generateURL(String url) {
        return ServingWebContentApplication.DOMAIN_FULL + url;
    }
}
