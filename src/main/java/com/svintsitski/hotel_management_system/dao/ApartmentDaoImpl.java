package com.svintsitski.hotel_management_system.dao;

import com.svintsitski.hotel_management_system.model.Apartment;
import com.svintsitski.hotel_management_system.model.ApartmentType;
import com.svintsitski.hotel_management_system.model.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class ApartmentDaoImpl implements ApartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private java.lang.Object Object;

    @Override
    public ResultQuery findAll(int start, int total, String sort) {
        String sql = "SELECT a.id, a.number, a.type_id, a.description AS desc_apart, a_t.price,\n" +
                " a_t.rooms_number, a_t.places_number, a_t.type, a_t.description AS desc_type\n" +
                "FROM hotel.apartments AS a\n" +
                "LEFT JOIN hotel.apartment_type a_t ON a_t.id = a.type_id\n" +
                "ORDER BY " + sort + " ASC " +
                " LIMIT " + (start-1) + "," + total + ";";

        List<Map<String, java.lang.Object>> rows = jdbcTemplate.queryForList(sql, Object);

        List<Apartment> apartmentList = new ArrayList<>();
        List<ApartmentType> apartmentTypeList = new ArrayList<>();

        for (Map<String, java.lang.Object> row : rows) {
            Apartment apartment = new Apartment();
            apartment.setId((Integer) row.get("id"));
            apartment.setNumber((Byte) row.get("number"));
            apartment.setType_id((Integer) row.get("type_id"));
            apartment.setDescription((String) row.get("description"));

            ApartmentType apartmentType = new ApartmentType();
            apartmentType.setId((Integer) row.get("id"));
            apartmentType.setPrice((Float) row.get("price"));
            apartmentType.setRooms_number((Byte) row.get("rooms_number"));
            apartmentType.setPlaces_number((Byte) row.get("places_number"));
            apartmentType.setType((String) row.get("type"));
            apartmentType.setDescription((String) row.get("desc_type"));

            apartmentList.add(apartment);
            apartmentTypeList.add(apartmentType);
        }

        String sql2 = "SELECT COUNT(*) FROM apartment_type;";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class);
        return new ResultQuery(count, Arrays.asList(apartmentList, apartmentTypeList));
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
