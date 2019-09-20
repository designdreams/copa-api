package com.designdreams.copass.payload;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.bean.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SearchTripResponse {

    private List<Trip> TripList;

    public List<Trip> getTripList() {
        return TripList;
    }

    public void setTripList(List<Trip> TripList) {
        this.TripList = TripList;
    }
}