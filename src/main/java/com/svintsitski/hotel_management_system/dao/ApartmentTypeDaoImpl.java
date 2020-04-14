package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.support.ResultQuery;
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

    public List<ApartmentType> findAll(int start, int total, String sort) {

        String sql = "SELECT * FROM apartment_type " +
                "ORDER BY " + sort + ";";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ApartmentType.class));

    }

    public ApartmentType findById(int id) {
        String sql = "SELECT * FROM apartment_type WHERE id=" + id + ";";
        List<ApartmentType> apartmentTypeList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ApartmentType.class));
        ApartmentType apartmentType = apartmentTypeList.stream().findFirst().orElse(null);
        return apartmentType;
    }

    @Override
    public int add(ApartmentType apartmentType) {
        String sql = "INSERT INTO `hotel`.`apartment_type` (`price`, " +
                "`rooms_number`, " +
                "`places_number`, " +
                "`type`, " +
                "`description`) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                apartmentType.getPrice(),
                apartmentType.getRooms_number(),
                apartmentType.getPlaces_number(),
                apartmentType.getType(),
                apartmentType.getDescription());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int update(ApartmentType apartmentType) {
        String sql = "UPDATE `hotel`.`apartment_type` " +
                "SET `price` = ?, " +
                "`rooms_number` = ?," +
                " `places_number` = ?, " +
                "`type` = ?, " +
                "`description` = ? " +
                "WHERE (`id` = ?);";
        jdbcTemplate.update(sql,
                apartmentType.getPrice(),
                apartmentType.getRooms_number(),
                apartmentType.getPlaces_number(),
                apartmentType.getType(),
                apartmentType.getDescription(),
                apartmentType.getId());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM `hotel`.`apartment_type` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }
}
