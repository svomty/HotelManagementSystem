package com.svintsitski.hotel_management_system.model;

import java.sql.Date;
import java.util.Objects;

public class Customer {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private Date birth_date;
    private String passport_serial_number;
    private String identification_number;
    private Date date_issue_passport;
    private String issuing_authority;
    private String registration_address;

    private Date date_entry_to_Belarus;
    private String insurance_policy_number;
    private String visa_number;
    private Date passport_validity_date;
    private String citizenship;

    public Customer() {
    }

    public Customer(int id, String surname, String name, String patronymic, Date birth_date,
                    String passport_serial_number, String identification_number, Date date_issue_passport,
                    String issuing_authority, String registration_address) {

        this(id, surname, name, patronymic, birth_date, passport_serial_number, identification_number,
                date_issue_passport, issuing_authority, registration_address, null,
                null, null, null, null);

    }

    public Customer(int id, String surname, String name, String patronymic, Date birth_date,
                    String passport_serial_number, String identification_number, Date date_issue_passport,
                    String issuing_authority, String registration_address, Date date_entry_to_Belarus,
                    String insurance_policy_number, String visa_number, Date passport_validity_date,
                    String citizenship) {
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
        this.date_entry_to_Belarus = date_entry_to_Belarus;
        this.insurance_policy_number = insurance_policy_number;
        this.visa_number = visa_number;
        this.passport_validity_date = passport_validity_date;
        this.citizenship = citizenship;
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

    public void setSurname(String surname) {
        this.surname = surname.trim().substring(0, 24);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim().substring(0, 19);
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic.trim().substring(0, 19);
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

    public void setPassport_serial_number(String passport_serial_number) {
        this.passport_serial_number = passport_serial_number.trim().substring(0, 11);
    }

    public String getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(String identification_number) {
        this.identification_number = identification_number.trim().substring(0, 16);
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
        this.issuing_authority = issuing_authority.trim().substring(0, 59);
    }

    public String getRegistration_address() {
        return registration_address;
    }

    public void setRegistration_address(String registration_address) {
        this.registration_address = registration_address.trim().substring(0, 59);
    }

    public Date getDate_entry_to_Belarus() {
        return date_entry_to_Belarus;
    }

    public void setDate_entry_to_Belarus(Date date_entry_to_Belarus) {
        this.date_entry_to_Belarus = date_entry_to_Belarus;
    }

    public String getInsurance_policy_number() {
        return insurance_policy_number;
    }

    public void setInsurance_policy_number(String insurance_policy_number) {
        this.insurance_policy_number = insurance_policy_number.trim().substring(0, 14);
    }

    public String getVisa_number() {
        return visa_number;
    }

    public void setVisa_number(String visa_number) {
        this.visa_number = visa_number.trim().substring(0, 14);
    }

    public Date getPassport_validity_date() {
        return passport_validity_date;
    }

    public void setPassport_validity_date(Date passport_validity_date) {
        this.passport_validity_date = passport_validity_date;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship.trim().substring(0, 14);
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
                Objects.equals(registration_address, customer.registration_address) &&
                Objects.equals(date_entry_to_Belarus, customer.date_entry_to_Belarus) &&
                Objects.equals(insurance_policy_number, customer.insurance_policy_number) &&
                Objects.equals(visa_number, customer.visa_number) &&
                Objects.equals(passport_validity_date, customer.passport_validity_date) &&
                Objects.equals(citizenship, customer.citizenship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, birth_date, passport_serial_number, identification_number,
                date_issue_passport, issuing_authority, registration_address, date_entry_to_Belarus,
                insurance_policy_number, visa_number, passport_validity_date, citizenship);
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
                ", date_entry_to_Belarus=" + date_entry_to_Belarus +
                ", insurance_policy_number='" + insurance_policy_number + '\'' +
                ", visa_number='" + visa_number + '\'' +
                ", passport_validity_date=" + passport_validity_date +
                ", citizenship='" + citizenship + '\'' +
                '}';
    }
}
