package com.example.bigbowlxp.repositories;

import com.example.bigbowlxp.models.Beverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BeverageRepo {

    private JdbcTemplate jdbcTemplate;

    public List<Beverage> fetchBeverages() {
        String sql = "SELECT * FROM beverages";
        RowMapper<Beverage> rowMapper = new BeanPropertyRowMapper<>(Beverage.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void createBeverage(Beverage beverage) {
        String sql = "INSERT INTO beverages(beverage_id, name, price) VALUES (?,?,?)";
        jdbcTemplate.update(sql, beverage.getBeverageId(), beverage.getName(), beverage.getPrice());
    }

    public void deleteBeverage(int id) {
        String sql = "DELETE FROM booking_beverages WHERE beverage_id = ?";
        jdbcTemplate.update(sql, id);

        sql = "DELETE FROM beverages WHERE beverage_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updatePrice(double price, int id) {
        String sql = "UPDATE beverages SET price = ? WHERE beverage_id = ?";
        jdbcTemplate.update(sql, price, id);
    }

    public void updateBeverage(Beverage b){
        String sql = "UPDATE beverages   SET name = ?, price = ? WHERE beverage_id = ?";
        jdbcTemplate.update(sql, b.getName(), b.getPrice(), b.getBeverageId());

    }

    public Beverage findBeverageById(int beverageId){
        String sql = "SELECT * FROM beverages WHERE beverage_id = ?";
        RowMapper<Beverage> rowMapper = new BeanPropertyRowMapper<>(Beverage.class);
        Beverage b = jdbcTemplate.queryForObject(sql, rowMapper, beverageId);
        return b;
    }
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
