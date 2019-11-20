package com.designdreams.copass.payload;

import com.designdreams.copass.bean.Trip;

import java.util.List;

public class ReadTripResponse {

    private List<Trip> TripList;

    public List<Trip> getTripList() {
        return TripList;
    }

    public void setTripList(List<Trip> TripList) {
        this.TripList = TripList;
    }


}
