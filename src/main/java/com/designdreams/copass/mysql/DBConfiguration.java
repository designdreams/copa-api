package com.designdreams.copass.mysql;

import javax.sql.DataSource;

import com.designdreams.copass.mysql.TravellerDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

//@Configuration
//@PropertySource("classpath:application.properties")
//@Component
public class DBConfiguration {


//    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
//    private String jdbcUrl = "jdbc:mysql://localhost:3306/copassdb";
//    private String jdbcUser = "root";
//    private String jdbcPass = "admin123";
//
//    @Bean
//    public DataSource dataSource() {
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(jdbcDriver);
//        dataSource.setUrl(jdbcUrl);//change url
//        dataSource.setUsername(jdbcUser);//change userid
//        dataSource.setPassword(jdbcPass);//change pwd
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        jdbcTemplate.setDataSource(dataSource());
//        return jdbcTemplate;
//    }
//
////    @Bean
////    public TravellerDAO travellerDAO() {
////        TravellerDAOImpl travellerDAOObj = new TravellerDAOImpl();
////        ((TravellerDAOImpl) travellerDAOObj).setJdbcTemplate(jdbcTemplate());
////        return travellerDAOObj;
////    }
//
////    @Bean
////    public TripDAO tripDAO(){
////        TripDAO tripDAO = new TripSqlDAOImpl();
////        ((TripSqlDAOImpl) tripDAO).setJdbcTemplate(jdbcTemplate());
////        return tripDAO;
////    }

}