package com.designdreams.copass.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SearchTripRequest {

    @JsonProperty(required = true)
    private String userId;

    @JsonProperty(required = true)
    @NotEmpty(message = "Source cannot be empty")
    private String source;

    @JsonProperty(required = true)
    @NotEmpty(message = "Destination cannot be empty")
    private String destination;

    @JsonProperty
    @Size(max = 16, message = "Invalid Date, Limit to 16 characters")
    private String travelStartDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTravelStartDate() {
        return travelStartDate;
    }

    public void setTravelStartDate(String travelStartDate) {
        this.travelStartDate = travelStartDate;
    }

}
