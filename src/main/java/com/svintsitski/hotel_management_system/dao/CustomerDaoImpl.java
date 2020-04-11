package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Customer;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResultQuery findAll(int start, int total, String sort) throws Exception {
        String sql = "SELECT * FROM customers " +
                "ORDER BY " + sort + " ASC " +
                " LIMIT " + (start - 1) + "," + total + ";";

        List<Customer> customerList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Customer.class));

        String sql2 = "SELECT COUNT(*) FROM customers;";
        int count = Objects.requireNonNull(jdbcTemplate.queryForObject(sql2, Integer.class));
        return new ResultQuery(count, customerList);
    }

    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customers WHERE id=" + id + ";";
        List<Customer> customerList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Customer.class));
        Customer customer = customerList.stream().findFirst().orElse(null);
        return customer;
    }

    @Override
    public int add(Customer customer) {
        String sql = "INSERT INTO `hotel`.`customers` (`surname`, " +
                "`name`, " +
                "`patronymic`, " +
                "`birth_date`," +
                " `passport_serial_number`, " +
                "`identification_number`, " +
                "`date_issue_passport`, " +
                "`issuing_authority`," +
                " `registration_address`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql,
                customer.getSurname(),
                customer.getName(),
                customer.getPatronymic(),
                customer.getBirth_date(),
                customer.getPassport_serial_number(),
                customer.getIdentification_number(),
                customer.getDate_issue_passport(),
                customer.getIssuing_authority(),
                customer.getRegistration_address());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int update(Customer customer) {
        String sql = "UPDATE `hotel`.`customers` " +
                "SET `surname` = ?, " +
                "`name` = ?, " +
                "`patronymic` = ?," +
                "`birth_date` = ?, " +
                "`passport_serial_number` = ?, " +
                "`identification_number` = ? " +
                ", `date_issue_passport` = ?, " +
                "`issuing_authority` = ?, " +
                "`registration_address` = ? " +
                "WHERE (`id` = ?);";
        jdbcTemplate.update(sql,
                customer.getSurname(),
                customer.getName(),
                customer.getPatronymic(),
                customer.getBirth_date(),
                customer.getPassport_serial_number(),
                customer.getIdentification_number(),
                customer.getDate_issue_passport(),
                customer.getIssuing_authority(),
                customer.getRegistration_address(),
                customer.getId());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM `hotel`.`customers` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }
}
