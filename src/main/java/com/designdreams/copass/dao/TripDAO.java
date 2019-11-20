package com.designdreams.copass.dao;

import com.designdreams.copass.bean.Trip;

import java.util.List;

public interface TripDAO {

     String createTrip(String uuid, String emailId, Trip trip);

     String getTripDetails(String uuid, String emailId, String tripId);

     List<Trip> getTripDetailsList(String emailId);

     List<Trip> searchTripDetailsList(String traceId, String src, String dest, String date);

}
