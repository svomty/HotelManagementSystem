package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Accommodation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional
@Repository
public class AccommodationDaoImpl implements AccommodationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AccommodationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Accommodation> findAll(String sort) {
        String sql = "SELECT * FROM hotel_accommodation " +
                "ORDER BY " + sort + ";";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Accommodation.class));
    }

    @Override
    public Accommodation findById(int id) {
        String sql = "SELECT * FROM hotel_accommodation WHERE id=" + id + ";";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Accommodation.class)).stream().findFirst().orElse(null);
    }

    @Override
    public List<Accommodation> findByApartmentId(int id) {
        String sql = "SELECT * FROM hotel_accommodation WHERE apartment_id=" + id + ";";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Accommodation.class));
    }

    @Override
    public List<Accommodation> findByCustomerId(int id) {
        String sql = "SELECT * FROM hotel_accommodation WHERE customer_id=" + id + ";";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Accommodation.class));
    }

    @Override
    public int add(Accommodation accommodation) {
        String sql = "INSERT INTO `hotel`.`hotel_accommodation` " +
                "(`arrival_date`," +
                " `departure_date`," +
                " `customer_id`," +
                " `apartment_id`)" +
                " VALUES (?, ?, ?, ?);";

        jdbcTemplate.update(sql,
                accommodation.getArrival_date(),
                accommodation.getDeparture_date(),
                accommodation.getCustomer_id(),
                accommodation.getApartment_id());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int update(Accommodation accommodation) {

        String sql = "UPDATE `hotel`.`hotel_accommodation` SET " +
                "`arrival_date` = ?, " +
                "`departure_date` = ?, " +
                "`customer_id` = ?," +
                "`apartment_id` = ? WHERE (`id` = ?);";

        jdbcTemplate.update(sql,
                accommodation.getArrival_date(),
                accommodation.getDeparture_date(),
                accommodation.getCustomer_id(),
                accommodation.getApartment_id(),
                accommodation.getId());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int delete(int id) {

        String sql = "DELETE FROM `hotel`.`hotel_accommodation` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }
}
