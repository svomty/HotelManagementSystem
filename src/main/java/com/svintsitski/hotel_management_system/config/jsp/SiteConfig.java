package com.svintsitski.hotel_management_system.config.jsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SiteConfig {

    Properties properties = new Properties();
    FileInputStream fis = null;
    FileOutputStream fos = null;
    Operation operation;
    String fileName = "./src/main/resources/siteConfig.ini";
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteConfig.class);

    public SiteConfig(Operation operation) {
        this.operation = operation;
    }

    public String execute(String args[]) {
        String result = "..";
        try {

            fis = new FileInputStream(fileName);
            String property = args[0];

            properties.load(new InputStreamReader(fis, StandardCharsets.UTF_8));

            if (operation == Operation.SET) {
                String value = args[1];
                properties.setProperty(property, value);

                fos = new FileOutputStream(fileName);
                properties.store(new OutputStreamWriter(fos, StandardCharsets.UTF_8), null);
            }

            result =  properties.getProperty(property);

        } catch (Exception e) {

            LOGGER.error(e.getMessage());

        } finally {

            if (null != fis) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }

            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
            return result;
        }
    }
}
