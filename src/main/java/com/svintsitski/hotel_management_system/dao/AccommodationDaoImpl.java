package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Accommodation;
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
public class AccommodationDaoImpl implements AccommodationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AccommodationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        String sql = "SELECT * FROM hotel_accommodation " +
                "ORDER BY " + sort + " ASC " +
                " LIMIT " + (start - 1) + "," + total + ";";

        List<Accommodation> accommodationList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Accommodation.class));

        String sql2 = "SELECT COUNT(*) FROM hotel_accommodation;";
        int count = Objects.requireNonNull(jdbcTemplate.queryForObject(sql2, Integer.class));
        return new ResultQuery(count, accommodationList);
    }

    @Override
    public Accommodation findById(int id) {
        String sql = "SELECT * FROM hotel_accommodation WHERE id=" + id + ";";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Accommodation.class)).stream().findFirst().orElse(null);
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
