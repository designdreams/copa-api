package com.designdreams.copass.payload;

import com.designdreams.copass.bean.Trip;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateItineraryRequest {

    @JsonProperty(required = true)
    @NotEmpty(message = "travellerId(email)is required")
    private String travellerId;

    @JsonProperty(required = true)
    @Valid
    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    @Override
    public String toString() {
        return "CreateItineraryRequest{" +
                "travellerId='" + travellerId + '\'' +
                ", itinerary=" + trip.toString() +
                '}';
    }

}
