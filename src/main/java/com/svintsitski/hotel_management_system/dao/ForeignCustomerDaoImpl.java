package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.ForeignCustomer;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ForeignCustomerDaoImpl implements ForeignCustomerDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ForeignCustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ForeignCustomer> findAll(String sort) {
        String sql = "SELECT * FROM foreign_customers;";
        List<ForeignCustomer> customerList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ForeignCustomer.class));
        return customerList;
    }

    @Override
    public ForeignCustomer findById(int id) {
        String sql = "SELECT * FROM foreign_customers WHERE customer_id=" + id + ";";
        List<ForeignCustomer> customerList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ForeignCustomer.class));
        ForeignCustomer customer = customerList.stream().findFirst().orElse(new ForeignCustomer());
        return customer;
    }

    @Override
    public int add(ForeignCustomer customer) {
        String sql = "INSERT INTO `hotel`.`foreign_customers` (`customer_id`," +
                " `date_entry_to_Belarus`," +
                " `insurance_policy_number`," +
                " `visa_number`," +
                " `passport_validity_date`," +
                " `citizenship`," +
                " `insurance_policy_issue_date`," +
                " `insurance_policy_validity`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                customer.getCustomer_id(),
                customer.getDate_entry_to_Belarus(),
                customer.getInsurance_policy_number(),
                customer.getVisa_number(),
                customer.getPassport_validity_date(),
                customer.getCitizenship(),
                customer.getInsurance_policy_issue_date(),
                customer.getInsurance_policy_validity());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int update(ForeignCustomer customer) {
        String sql = "UPDATE `hotel`.`foreign_customers` SET" +
                " `date_entry_to_Belarus` = ?," +
                " `insurance_policy_number` = ?," +
                "`visa_number` = ?," +
                " `passport_validity_date` = ?," +
                " `citizenship` = ? " +
                ", `insurance_policy_issue_date` = ?," +
                " `insurance_policy_validity` = ? WHERE (`customer_id` = ?);";
        jdbcTemplate.update(sql,
                customer.getDate_entry_to_Belarus(),
                customer.getInsurance_policy_number(),
                customer.getVisa_number(),
                customer.getPassport_validity_date(),
                customer.getCitizenship(),
                customer.getInsurance_policy_issue_date(),
                customer.getInsurance_policy_validity(),
                customer.getCustomer_id());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM `hotel`.`foreign_customers` WHERE (`customer_id` = '" + id + "');";
        jdbcTemplate.execute(sql);

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }
}
