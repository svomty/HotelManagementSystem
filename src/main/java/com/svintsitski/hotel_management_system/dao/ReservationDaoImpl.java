package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ReservationDaoImpl implements ReservationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Reservation> findAll(String sort) {
        String sql = "SELECT * FROM reservation " +
                "ORDER BY " + sort + ";";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Reservation.class));
    }

    @Override
    public Reservation findById(int id) {
        String sql = "SELECT * FROM reservation WHERE id=" + id + ";";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Reservation.class)).stream().findFirst().orElse(null);
    }

    @Override
    public List<Reservation> findByApartmentId(int id) {
        String sql = "SELECT * FROM reservation WHERE apartment_id=" + id + ";";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Reservation.class));
    }

    @Override
    public int add(Reservation reservation) {
        String sql = "INSERT INTO `hotel`.`reservation` " +
                "(`arrival_date`," +
                " `departure_date`," +
                " `full_name`," +
                " `apartment_id`," +
                " `arrived`," +
                " `customer_phone`)" +
                " VALUES (?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql,
                reservation.getArrival_date(),
                reservation.getDeparture_date(),
                reservation.getFull_name(),
                reservation.getApartment_id(),
                reservation.getArrived(),
                reservation.getCustomer_phone());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int update(Reservation reservation) {

        String sql = "UPDATE `hotel`.`reservation` SET " +
                "`arrival_date` = ?, " +
                "`departure_date` = ?, " +
                "`full_name` = ?," +
                "`apartment_id` = ?," +
                "`arrived` = ?," +
                "`customer_phone` = ? WHERE (`id` = ?);";

        jdbcTemplate.update(sql,
                reservation.getArrival_date(),
                reservation.getDeparture_date(),
                reservation.getFull_name(),
                reservation.getApartment_id(),
                reservation.getArrived(),
                reservation.getCustomer_phone(),
                reservation.getId());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int delete(int id) {

        String sql = "DELETE FROM `hotel`.`reservation` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }
}
