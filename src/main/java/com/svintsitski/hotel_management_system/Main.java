package com.svintsitski.hotel_management_system;

import com.svintsitski.hotel_management_system.model.enam.Type;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Main {

    public static void main(String args[]) {
        Main ini = new Main();
        ini.doit();
    }

    public void doit() {

        Properties properties = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("./src/main/resources/siteConfig.ini");
            properties.load(fis);

            System.out.println("user = " + properties.getProperty("user"));

            properties.store(new FileOutputStream("./src/main/resources/siteConfig.ini"), null);

            properties.list(System.out);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}
