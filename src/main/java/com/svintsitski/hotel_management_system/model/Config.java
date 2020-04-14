package com.svintsitski.hotel_management_system.model;

import com.svintsitski.hotel_management_system.config.jsp.Operation;
import com.svintsitski.hotel_management_system.config.jsp.SiteConfig;

import java.util.Objects;

public class Config {
    private String phone;
    private String email;
    private String address;
    private String name;
    private int countElem;
    private String login;
    private String password;

    SiteConfig siteConfig;

    private static volatile Config instance;

    public static Config getInstance() {
        Config localInstance = instance;
        if (localInstance == null) {
            synchronized (Config.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Config();
                }
            }
        }
        return localInstance;
    }

    private Config() {
        siteConfig = new SiteConfig(Operation.GET);

        password = siteConfig.execute(new String[]{"password"});
        address = siteConfig.execute(new String[]{"address"});
        countElem = Integer.parseInt(siteConfig.execute(new String[]{"countElem"}));
        email = siteConfig.execute(new String[]{"email"});
        login = siteConfig.execute(new String[]{"user"});
        name = siteConfig.execute(new String[]{"hotelName"});
        phone = siteConfig.execute(new String[]{"phone"});

    }

    public void overwriteProperties(Config config) {

        siteConfig = new SiteConfig(Operation.SET);

        instance.password = siteConfig.execute(new String[]{"password", config.getPassword()});
        instance.address = siteConfig.execute(new String[]{"address", config.getAddress()});
        instance.countElem = Integer.parseInt(siteConfig.execute(
                new String[]{"countElem", String.valueOf(config.getCountElem())}
        ));
        instance.email = siteConfig.execute(new String[]{"email", config.getEmail()});
        instance.login = siteConfig.execute(new String[]{"user", config.getLogin()});
        instance.name = siteConfig.execute(new String[]{"hotelName", config.getName()});
        instance.phone = siteConfig.execute(new String[]{"phone", config.getPhone()});

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountElem() {
        return countElem;
    }

    public void setCountElem(String countElem) {
        this.countElem = Integer.parseInt(countElem);
    }

    public void setCountElem(int countElem) {
        this.countElem = countElem;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Config{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", countElem='" + countElem + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return Objects.equals(phone, config.phone) &&
                Objects.equals(email, config.email) &&
                Objects.equals(address, config.address) &&
                Objects.equals(name, config.name) &&
                Objects.equals(countElem, config.countElem) &&
                Objects.equals(login, config.login) &&
                Objects.equals(password, config.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email, address, name, countElem, login, password);
    }
}
