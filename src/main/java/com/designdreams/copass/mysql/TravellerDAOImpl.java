//package com.designdreams.copass.mysql;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TravellerDAOImpl implements TravellerDAO {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Value("${getTravellerInfoQuery}")
//    private String getTravellerInfoQuery;
//
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public String getTravellerInfo(int id) {
//        String sql = getTravellerInfoQuery;
//        String name = null;
//
//        try {
//            System.out.println(" QUERY : " + getTravellerInfoQuery);
//            name = jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
//
//        } catch (EmptyResultDataAccessException e) {
//            name = "NOT_FOUND";
//
//        } catch (DataAccessException e) {
//            name = "DB_ERROR";
//            e.printStackTrace();
//        }
//
//        return name;
//    }
//
//}
