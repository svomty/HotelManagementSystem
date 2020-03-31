package com.svintsitski.hotel_management_system.dao;

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
public class ApartmentTypeDaoImpl implements ApartmentTypeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApartmentTypeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResultQuery findAll (int start, int total, String sort) {
        String sql = "SELECT * FROM apartment_type " +
                "ORDER BY " + sort + " ASC " +
                " LIMIT " + (start-1) + "," + total + ";";
        List<ApartmentType> apartmentTypeList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ApartmentType.class));
        String sql2 = "SELECT COUNT(*) FROM apartment_type;";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class);
        return new ResultQuery(count, apartmentTypeList);
    }

    public ApartmentType findById(int id) {
        String sql = "SELECT * FROM apartment_type WHERE id=" + id + ";";
        List<ApartmentType> apartmentTypeList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ApartmentType.class));
        return apartmentTypeList.stream().findFirst().orElse(null);
    }

    @Override
    public void add(ApartmentType apartmentType) {
        String sql = "INSERT INTO `hotel`.`apartment_type` (`price`, `rooms_number`, `places_number`, `type`," +
                " `description`) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, apartmentType.getPrice(), apartmentType.getRooms_number(),
                apartmentType.getPlaces_number(), apartmentType.getType(), apartmentType.getDescription());
    }

    @Override
    public void update(ApartmentType apartmentType) {
        String sql = "UPDATE `hotel`.`apartment_type` SET `price` = ?, `rooms_number` = ?," +
                " `places_number` = ?, `type` = ?, `description` = ? WHERE (`id` = ?);";
        jdbcTemplate.update(sql, apartmentType.getPrice(), apartmentType.getRooms_number(),
                apartmentType.getPlaces_number(), apartmentType.getType(), apartmentType.getDescription(),
                apartmentType.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM `hotel`.`apartment_type` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);
    }
}
