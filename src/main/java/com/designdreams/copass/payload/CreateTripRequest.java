package com.designdreams.copass.payload;

import com.designdreams.copass.bean.Trip;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTripRequest {

    @JsonProperty(required = true)
    @Valid
    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "CreateItineraryRequest{" +
                ", itinerary=" + (null!=trip?trip.toString():null) +
                '}';
    }

}
