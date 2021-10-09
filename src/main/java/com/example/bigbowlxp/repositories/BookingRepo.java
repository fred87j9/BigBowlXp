package com.example.bigbowlxp.repositories;

import com.example.bigbowlxp.models.Activity;
import com.example.bigbowlxp.models.AirHockeyTable;
import com.example.bigbowlxp.models.Booking;
import com.example.bigbowlxp.models.BookingBeverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepo{

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getTemplate() {
        return jdbcTemplate;
    }

    public void createBooking(Booking b ){
        System.out.println(b.getActivity().toString().length());
        String sql = "INSERT INTO bookings(customer_id, activity, date, start_time, duration) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, b.getCustomer_id(), b.getActivity().toString(), b.getDate(), b.getStartTime(), b.getDuration());

    }

    public Booking fetchBookingById(int id){
        String sql = "SELECT booking_id, b.customer_id, first_name, last_name, phone, activity, date, start_time, duration " +
                "FROM sql4438617.bookings b " +
                "JOIN sql4438617.customers USING (customer_id) " +
                "WHERE booking_id = ?";
        RowMapper<Booking> bookingRowMapper = new BeanPropertyRowMapper<>(Booking.class);
        Booking booking = jdbcTemplate.queryForObject(sql, bookingRowMapper, id);
        booking.setBeverages(fetchBookingBeveragesByBookingId(id));
        return booking;
    }

    public void deleteBooking(int id){
        String sql = "DELETE FROM booking_beverages WHERE bookingId = ?";
        jdbcTemplate.update(sql,id);

        sql ="DELETE FROM bookings WHERE bookingId = ?";
        jdbcTemplate.update(sql,id);
    }

    public void deleteJoinedBookings(int id){
        String sql = "DELETE FROM bookings_unit WHERE booking_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ArrayList<Booking> bowlingBooking(){
        ArrayList<Booking> listOfBowlingBooking = new ArrayList<>();
        String sql = "";
        return null;
    }
public List<Booking> fetchBookingByActivity(String activity){

        String sql = "SELECT * FROM bookings WHERE activity = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);

        return jdbcTemplate.query(sql,rowMapper,activity);
}


    public List<Booking> fetchBookingByDay(LocalDate day, Activity activity) {
        ArrayList<Booking> fetchBookingByDay = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE date = ? AND activity = ?";
        RowMapper<Booking> rowMapper = new BeanPropertyRowMapper<>(Booking.class);
        return jdbcTemplate.query(sql, rowMapper, day, activity.toString());
    }



    public List<Booking> fetchAirHockeyTableBooking(){
        String sql = "SELECT booking_id, b.customer_id, first_name, last_name, phone, activity, date, start_time, duration" +
                " FROM sql4438617.bookings b " +
                "JOIN sql4438617.customers USING (customer_id) " +
                "WHERE activity = 'AIRHOCKEY'" +
                "ORDER BY date, start_time";
        RowMapper<Booking> bookingRowMapper = new BeanPropertyRowMapper<>(Booking.class);
        List<Booking> list = jdbcTemplate.query(sql, bookingRowMapper);
        for(Booking booking : list){
            booking.setBeverages(fetchBookingBeveragesByBookingId(booking.getBookingId()));
        }
        return list;
    }

    public List<Booking> fetchBowlingBooking(){
        String sql = "SELECT booking_id, b.customer_id, first_name, last_name, phone, activity, date, start_time, duration" +
                " FROM sql4438617.bookings b " +
                "JOIN sql4438617.customers USING (customer_id) " +
                "WHERE activity = 'BOWLING'" +
                "ORDER BY date, start_time";
        RowMapper<Booking> bookingRowMapper = new BeanPropertyRowMapper<>(Booking.class);
        List<Booking> list = jdbcTemplate.query(sql, bookingRowMapper);
        for(Booking booking : list){
            booking.setBeverages(fetchBookingBeveragesByBookingId(booking.getBookingId()));
        }
        return list;
    }

    public List<Booking> fetchRestaurantBooking(){
        String sql = "SELECT booking_id, b.customer_id, first_name, last_name, phone, activity, date, start_time, duration" +
                " FROM sql4438617.bookings b " +
                "JOIN sql4438617.customers USING (customer_id) " +
                "WHERE activity = 'RESTAURANT'" +
                "ORDER BY date, start_time";
        RowMapper<Booking> bookingRowMapper = new BeanPropertyRowMapper<>(Booking.class);
        List<Booking> list = jdbcTemplate.query(sql, bookingRowMapper);
        for(Booking booking : list){
            booking.setBeverages(fetchBookingBeveragesByBookingId(booking.getBookingId()));
        }
        return list;
    }

    public List<BookingBeverage> fetchBookingBeveragesByBookingId(int id){
        String sql = "SELECT booking_id, beverages.beverage_id, name, price, amount FROM booking_beverages " +
                "JOIN beverages USING ( beverage_id) " +
                "WHERE booking_id = ?";
        RowMapper<BookingBeverage> bookingBeverageRowMapper = new BeanPropertyRowMapper<>(BookingBeverage.class);
        List<BookingBeverage> list = jdbcTemplate.query(sql, bookingBeverageRowMapper, id);
        return list;
    }

    public void editBookingBeverages(BookingBeverage bb){
        String sql = "UPDATE booking_beverages SET amount = ? WHERE booking_id = ? AND beverage_id = ?";
        jdbcTemplate.update(sql, bb.getAmount(), bb.getBookingId(), bb.getBeverageId());
    }

    public void editBooking(int id){
        String sql = "UPDATE sql4438617.bookings SET date = ?, start_time = ?, duration = ? WHERE booking_id = ?";
        jdbcTemplate.update(sql);

    }
}
