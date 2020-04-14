package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.database.Apartment;
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
public class ApartmentDaoImpl implements ApartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private java.lang.Object Object;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        /*String sql = "SELECT * FROM apartments " +
                "ORDER BY " + sort + " ASC " +
                " LIMIT " + (start - 1) + "," + total + ";";*/

        String sql = "SELECT * FROM apartments " +
                "ORDER BY " + sort + ";";

        List<Apartment> apartmentList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Apartment.class));

        String sql2 = "SELECT COUNT(*) FROM apartments;";
        int count = Objects.requireNonNull(jdbcTemplate.queryForObject(sql2, Integer.class));
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
    public List<Apartment> findByType(int id) {
        String sql = "SELECT * FROM apartments WHERE type_id=" + id + ";";

        List<Apartment> apartmentTypeList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Apartment.class));
        return apartmentTypeList;
    }

    @Override
    public int add(Apartment apartment) {
        String sql = "INSERT INTO `hotel`.`apartments` (`number`, " +
                "`type_id` " +
                ") VALUES (?, ?);";
        jdbcTemplate.update(sql,
                apartment.getNumber(),
                apartment.getType_id());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int update(Apartment apartment) {
        String sql = "UPDATE `hotel`.`apartments` SET `number` = ?, " +
                "`type_id` = ?" +
                " WHERE (`id` = ?);";
        jdbcTemplate.update(sql,
                apartment.getNumber(),
                apartment.getType_id(),
                apartment.getId());

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM `hotel`.`apartments` WHERE (`id` = '" + id + "');";
        jdbcTemplate.execute(sql);

        return ResultQuery.getLastInsertId(jdbcTemplate);
    }
}
