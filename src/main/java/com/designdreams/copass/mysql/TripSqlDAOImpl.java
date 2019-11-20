//package com.designdreams.copass.mysql;
//
//import com.designdreams.copass.bean.Trip;
//import com.designdreams.copass.mysql.TripRowMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class TripSqlDAOImpl  {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Value("${createTripInfoQuery}")
//    private String createTripInfoQuery;
//
//    @Value("${getTripInfoQuery}")
//    private String getTripInfoQuery;
//
//    @Value("${getTripListQuery}")
//    private String getTripListQuery;
//
//    @Value("${searchTripListQuery}")
//    private String searchTripListQuery;
//
//    @Value("${searchTripListWithDateQuery}")
//    private String searchTripListWithDateQuery;
//
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public String createTrip(String uuid, String userId, Trip trip) {
//
//        String sql = createTripInfoQuery;
//        String status;
//
//        //values(default,'amy@efg.com','JFK','PHX','2020-02-03','Airways','Emirates','Family','N','N','Y','Y');
//
//        try {
//
//            System.out.println(" QUERY : " + getTripInfoQuery);
//
//            jdbcTemplate.update(sql, new Object[]{userId, trip.getSource(),
//                    trip.getDestination(), trip.getTravelStartDate(), trip.getMode(),
//                    trip.getAirways(), trip.getTravellingWith(), trip.isTicketBooked(),
//                    trip.isDomestic(), trip.isCanTakePackageInd(),
//                    trip.isFinalDestination()});
//
//            status = "SUCCESS";
//
//        } catch (DataAccessException e) {
//            status = "DB_ERROR";
//            e.printStackTrace();
//
//        } catch (Exception e) {
//            status = "FAILURE";
//            e.printStackTrace();
//        }
//
//        return status;
//    }
//
//
//    public String getTripDetails(int id) {
//
//        String sql = getTripInfoQuery;
//        String name = null;
//
//        try {
//            System.out.println(" QUERY : " + getTripInfoQuery);
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
//
//    }
//
//
//    public List<Trip> getTripDetailsList(String emailId) {
//
//        String sql = getTripListQuery;
//        String name = null;
//        List<Trip> tripList = null;
//
//        try {
//            System.out.println(" QUERY : " + getTripInfoQuery + "emailId:: " + emailId);
//            tripList = jdbcTemplate.query(sql, new Object[]{emailId}, new TripRowMapper());
//
//            tripList.stream()
//                    .map(trip -> trip.toString())
//                    .forEach(System.out::println);
//
//        } catch (EmptyResultDataAccessException e) {
//            name = "NOT_FOUND";
//            System.out.println(" NOT_FOUND :: QUERY : " + getTripInfoQuery + "emailId:: " + emailId);
//        } catch (DataAccessException e) {
//            name = "DB_ERROR";
//            e.printStackTrace();
//        }
//
//        return tripList;
//
//    }
//
//
//    public List<Trip> searchTripDetailsList(String traceId, String src, String dest, String date) {
//
//        String sql = searchTripListQuery;
//        String name = null;
//        List<Trip> tripList = null;
//
//        try {
//
//            System.out.println(" QUERY : " + searchTripListQuery + "src:: " + src +" dest:: "+dest);
//
//            if( null == date)
//                tripList = jdbcTemplate.query(sql, new Object[]{src, dest}, new TripRowMapper());
//            else
//                tripList = jdbcTemplate.query(sql, new Object[]{src, dest, date}, new TripRowMapper());
//
//            tripList.stream()
//                    .map(trip -> trip.toString())
//                    .forEach(System.out::println);
//
//        } catch (EmptyResultDataAccessException e) {
//            name = "NOT_FOUND";
//            System.out.println(" NOT_FOUND :: QUERY : " + getTripInfoQuery + "traceId:: " + traceId);
//
//        } catch (DataAccessException e) {
//            name = "DB_ERROR";
//            e.printStackTrace();
//        }
//
//        return tripList;
//
//    }
//
//}