//package com.designdreams.copass.mysql;
//
//import com.designdreams.copass.bean.Trip;
//import com.designdreams.copass.utils.AppUtils;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///*
//
//
//+--------------------+--------------+------+-----+---------+----------------+
//| Field              | Type         | Null | Key | Default | Extra          |
//+--------------------+--------------+------+-----+---------+----------------+
//| trip_id            | int(11)      | NO   | PRI | NULL    | auto_increment |
//| traveller_id       | varchar(320) | YES  |     | NULL    |                |
//| source             | varchar(20)  | NO   |     | NULL    |                |
//| destination        | varchar(20)  | NO   |     | NULL    |                |
//| travel_start_date  | date         | YES  |     | NULL    |                |
//| mode               | char(10)     | YES  |     | NULL    |                |
//| airways            | varchar(20)  | YES  |     | NULL    |                |
//| travellingWith     | varchar(10)  | YES  |     | NULL    |                |
//| isTicketBooked     | char(1)      | YES  |     | NULL    |                |
//| isDomestic         | char(1)      | YES  |     | NULL    |                |
//| canTakePackageInd  | char(1)      | YES  |     | NULL    |                |
//| isFinalDestination | char(1)      | YES  |     | NULL    |                |
//+--------------------+--------------+------+-----+---------+----------------+
//
// */
//
//public class TripRowMapper implements RowMapper<Trip> {
//
//    @Override
//    public Trip mapRow(ResultSet resultSet, int i) throws SQLException {
//
//        Trip trip = new Trip();
//
//        trip.setTripId(resultSet.getString("trip_id"));
//        trip.setTravellerId(resultSet.getString("traveller_id"));
//        trip.setSource(resultSet.getString("source"));
//        trip.setDestination(resultSet.getString("destination"));
//        trip.setMode(resultSet.getString("mode"));
//        trip.setTravelMonth(AppUtils.getMonthFromdate(resultSet.getString("travel_start_date")));
//        trip.setAirways(resultSet.getString("airways"));
//        trip.setTravellingWith(resultSet.getString("travellingWith"));
//        trip.setTravelStartDate(resultSet.getString("travel_start_date"));
//        trip.setCanTakePackageInd(resultSet.getString("canTakePackageInd").equalsIgnoreCase("Y"));
//        trip.setFinalDestination(resultSet.getString("isFinalDestination").equalsIgnoreCase("Y"));
//        trip.setTicketBooked(resultSet.getString("isTicketBooked").equalsIgnoreCase("Y"));
//
//        return trip;
//    }
//}
