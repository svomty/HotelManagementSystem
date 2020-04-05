package com.svintsitski.hotel_management_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Customer {
    private @Id @GeneratedValue int id;
    private String surname;
    private String name;
    private String patronymic;
    private Date birth_date;
    private String passport_serial_number;
    private String identification_number;
    private Date date_issue_passport;
    private String issuing_authority;
    private String registration_address;

    public Customer() {
    }

    public Customer(String surname, String name, String patronymic, Date birth_date,
                    String passport_serial_number, String identification_number, Date date_issue_passport,
                    String issuing_authority, String registration_address) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birth_date = birth_date;
        this.passport_serial_number = passport_serial_number;
        this.identification_number = identification_number;
        this.date_issue_passport = date_issue_passport;
        this.issuing_authority = issuing_authority;
        this.registration_address = registration_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0){
            this.id = id;
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws Exception {
        if (surname != null){
            this.surname = surname.trim();
        } else {
            throw new Exception("Фамилия не указана!");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name != null){
            this.name = name.trim();
        } else {
            throw new Exception("Имя не указано!");
        }
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        if (patronymic != null){
            this.patronymic = patronymic.trim();
        } else {
            this.patronymic = "отсутствует";
        }
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getPassport_serial_number() {
        return passport_serial_number;
    }

    public void setPassport_serial_number(String passport_serial_number) throws Exception {
        if (passport_serial_number != null){
            this.passport_serial_number = passport_serial_number.trim();
        } else {
            throw new Exception("Серийный номер паспорта не указан!");
        }
    }

    public String getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(String identification_number) throws Exception {
        if (identification_number != null){
            this.identification_number = identification_number.trim();
        } else {
            this.identification_number = "отсутствует";
        }
    }

    public Date getDate_issue_passport() {
        return date_issue_passport;
    }

    public void setDate_issue_passport(Date date_issue_passport) {
        this.date_issue_passport = date_issue_passport;
    }

    public String getIssuing_authority() {
        return issuing_authority;
    }

    public void setIssuing_authority(String issuing_authority) {
        if (issuing_authority != null){
            this.issuing_authority = issuing_authority.trim();
        } else {
            this.issuing_authority = "не указан";
        }
    }

    public String getRegistration_address() {
        return registration_address;
    }

    public void setRegistration_address(String registration_address) {
        if (registration_address != null){
            this.registration_address = registration_address.trim();
        } else {
            this.registration_address = "не указан";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(patronymic, customer.patronymic) &&
                Objects.equals(birth_date, customer.birth_date) &&
                Objects.equals(passport_serial_number, customer.passport_serial_number) &&
                Objects.equals(identification_number, customer.identification_number) &&
                Objects.equals(date_issue_passport, customer.date_issue_passport) &&
                Objects.equals(issuing_authority, customer.issuing_authority) &&
                Objects.equals(registration_address, customer.registration_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, birth_date, passport_serial_number, identification_number, date_issue_passport, issuing_authority, registration_address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birth_date=" + birth_date +
                ", passport_serial_number='" + passport_serial_number + '\'' +
                ", identification_number='" + identification_number + '\'' +
                ", date_issue_passport=" + date_issue_passport +
                ", issuing_authority='" + issuing_authority + '\'' +
                ", registration_address='" + registration_address + '\'' +
                '}';
    }
}
