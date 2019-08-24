package com.designdreams.copass.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class TripDAOImpl implements TripDAO{

    private JdbcTemplate jdbcTemplate;

    @Value("${getTripInfoQuery}")
    private String getTripInfoQuery;

    @Value("${getTripListQuery}")
    private String getTripListQuery;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTripDetails(int id) {
        String sql = getTripInfoQuery;
        String name = null;

        try {
            System.out.println(" QUERY : "+getTripInfoQuery);
            name =jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);

        } catch (EmptyResultDataAccessException e) {
            name = "NOT_FOUND";

        } catch (DataAccessException e) {
            name = "DB_ERROR";
            e.printStackTrace();
        }

        return name;
    }


    public String getTripDetailsList(String emailId){


        String sql = getTripListQuery;
        String name = null;

        try {
            System.out.println(" QUERY : "+getTripInfoQuery);
            name =jdbcTemplate.queryForObject(sql,new Object[]{emailId},String.class);

        } catch (EmptyResultDataAccessException e) {
            name = "NOT_FOUND";

        } catch (DataAccessException e) {
            name = "DB_ERROR";
            e.printStackTrace();
        }

        return name;

    }

}
