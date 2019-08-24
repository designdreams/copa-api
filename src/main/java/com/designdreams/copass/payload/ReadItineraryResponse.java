package com.designdreams.copass.payload;

import com.designdreams.copass.bean.Trip;

import java.util.List;

public class ReadItineraryResponse {

    private String travellerId;
    private List<Trip> TripList;

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public List<Trip> getTripList() {
        return TripList;
    }

    public void setTripList(List<Trip> TripList) {
        this.TripList = TripList;
    }


}
