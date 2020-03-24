package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.ApartmentType;
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

    public List<ApartmentType> getAll() {
        String sql = "SELECT * FROM apartment_type;";
        List<ApartmentType> apartmentTypeList = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ApartmentType.class));
        return apartmentTypeList;
    }

    @Override
    public ApartmentType findById(int id) {
        return null;
    }

    @Override
    public void add(ApartmentType apartmentType) {

    }

    @Override
    public void update(ApartmentType apartmentType) {

    }

    @Override
    public void delete(int id) {

    }
}
