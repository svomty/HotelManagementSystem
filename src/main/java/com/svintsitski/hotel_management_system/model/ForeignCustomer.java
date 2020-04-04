package com.svintsitski.hotel_management_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class ForeignCustomer {
    private int customer_id;
    private Date date_entry_to_Belarus;
    private String insurance_policy_number;
    private String visa_number;
    private Date passport_validity_date;
    private String citizenship;

    public ForeignCustomer() {
    }

    public ForeignCustomer(int customer_id, Date date_entry_to_Belarus, String insurance_policy_number, String visa_number, Date passport_validity_date, String citizenship) {
        this.customer_id = customer_id;
        this.date_entry_to_Belarus = date_entry_to_Belarus;
        this.insurance_policy_number = insurance_policy_number;
        this.visa_number = visa_number;
        this.passport_validity_date = passport_validity_date;
        this.citizenship = citizenship;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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
        if (insurance_policy_number != null){
            this.insurance_policy_number = insurance_policy_number.trim();
        } else {
            this.insurance_policy_number = "не указан";
        }
    }

    public String getVisa_number() {
        return visa_number;
    }

    public void setVisa_number(String visa_number) {
        if (visa_number != null){
            this.visa_number = visa_number.trim();
        } else {
            this.visa_number = "не указан";
        }
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
        if (citizenship != null){
            this.citizenship = citizenship.trim();
        } else {
            this.citizenship = "не указано";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForeignCustomer that = (ForeignCustomer) o;
        return Objects.equals(customer_id, that.customer_id) &&
                Objects.equals(date_entry_to_Belarus, that.date_entry_to_Belarus) &&
                Objects.equals(insurance_policy_number, that.insurance_policy_number) &&
                Objects.equals(visa_number, that.visa_number) &&
                Objects.equals(passport_validity_date, that.passport_validity_date) &&
                Objects.equals(citizenship, that.citizenship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date_entry_to_Belarus, insurance_policy_number, visa_number, passport_validity_date, citizenship);
    }

    @Override
    public String toString() {
        return "ForeignCustomer{" +
                "customer_id=" + customer_id +
                "date_entry_to_Belarus=" + date_entry_to_Belarus +
                ", insurance_policy_number='" + insurance_policy_number + '\'' +
                ", visa_number='" + visa_number + '\'' +
                ", passport_validity_date=" + passport_validity_date +
                ", citizenship='" + citizenship + '\'' +
                '}';
    }
}
