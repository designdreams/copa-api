package com.designdreams.copass.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class AlertRequest {

    @JsonProperty(required = true)
    @NotEmpty(message = "Valid trip id is required")
    private String tripId;

    @JsonProperty(required = true)
    @NotEmpty(message = "Valid TravellerId is required")
    private String travellerId;

    @JsonProperty(required = true)
    @NotEmpty(message = "Valid alert indicator  is required")
    private boolean alertEnableIndicator;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public boolean isAlertEnableIndicator() {
        return alertEnableIndicator;
    }

    public void setAlertEnableIndicator(boolean alertEnableIndicator) {
        this.alertEnableIndicator = alertEnableIndicator;
    }



}
