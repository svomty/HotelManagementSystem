package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ApartmentDaoImpl implements ApartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        String sql = "SELECT * FROM apartments " +
                "ORDER BY " + sort + " ASC " +
                " LIMIT " + (start-1) + "," + total + ";";
        List<Apartment> apartmentList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Apartment.class));
        String sql2 = "SELECT COUNT(*) FROM apartments;";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class);
        return new ResultQuery(count, apartmentList);
    }

    @Override
    public Apartment findById(int id) {
        String sql = "SELECT * FROM apartments WHERE id=" + id + ";";
        List<Apartment> apartmentTypeList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Apartment.class));
        return apartmentTypeList.stream().findFirst().orElse(null);
    }

    @Override
    public void add(Apartment apartment) {
        /*String sql = "INSERT INTO `hotel`.`apartments` (`price`, `rooms_number`, `places_number`, `type`," +
                " `description`) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, apartment.getPrice(), apartmentType.getRooms_number(),
                apartmentType.getPlaces_number(), apartmentType.getType(), apartmentType.getDescription());*/
    }

    @Override
    public void update(Apartment apartment) {
        /*String sql = "UPDATE `hotel`.`apartments` SET `price` = ?, `rooms_number` = ?," +
                " `places_number` = ?, `type` = ?, `description` = ? WHERE (`id` = ?);";
        jdbcTemplate.update(sql, apartmentType.getPrice(), apartmentType.getRooms_number(),
                apartmentType.getPlaces_number(), apartmentType.getType(), apartmentType.getDescription(),
                apartmentType.getId());*/
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM `hotel`.`apartments` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);
    }
}
