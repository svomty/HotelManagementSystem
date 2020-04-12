package com.svintsitski.hotel_management_system.model;

import com.svintsitski.hotel_management_system.config.jsp.Operation;
import com.svintsitski.hotel_management_system.config.jsp.SiteConfig;

import java.util.Objects;

public class Config {
    private String phone;
    private String email;
    private String address;
    private String name;
    private String yandexApi;
    private String countElem;
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
        countElem = siteConfig.execute(new String[]{"countElem"});
        email = siteConfig.execute(new String[]{"email"});
        login = siteConfig.execute(new String[]{"user"});
        name = siteConfig.execute(new String[]{"hotelName"});
        yandexApi = siteConfig.execute(new String[]{"yandexApi"});
        phone = siteConfig.execute(new String[]{"phone"});

    }

    public void overwriteProperties(Config config) {

        siteConfig = new SiteConfig(Operation.SET);

        instance.password = siteConfig.execute(new String[]{"password", config.getPassword()});
        instance.address = siteConfig.execute(new String[]{"address", config.getAddress()});
        instance.countElem = siteConfig.execute(new String[]{"countElem", config.getCountElem()});
        instance.email = siteConfig.execute(new String[]{"email", config.getEmail()});
        instance.login = siteConfig.execute(new String[]{"user", config.getLogin()});
        instance.name = siteConfig.execute(new String[]{"hotelName", config.getName()});
        instance.yandexApi = siteConfig.execute(new String[]{"yandexApi", config.getYandexApi()});
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

    public String getYandexApi() {
        return yandexApi;
    }

    public void setYandexApi(String yandexApi) {
        this.yandexApi = yandexApi;
    }

    public String getCountElem() {
        return countElem;
    }

    public void setCountElem(String countElem) {
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
                ", yandexApi='" + yandexApi + '\'' +
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
                Objects.equals(yandexApi, config.yandexApi) &&
                Objects.equals(countElem, config.countElem) &&
                Objects.equals(login, config.login) &&
                Objects.equals(password, config.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email, address, name, yandexApi, countElem, login, password);
    }
}
