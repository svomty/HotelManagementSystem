package com.svintsitski.hotel_management_system.model;

import com.svintsitski.hotel_management_system.ServingWebContentApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URL {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServingWebContentApplication.class);

    public static void IPInfo (String url, String ip, String requestType) {
        LOGGER.info(requestType +" [" + ip + "] requested " + generateURL(url));
    }

    public static String generateURL(String url) {
        return ServingWebContentApplication.DOMAIN_FULL + url;
    }
}
