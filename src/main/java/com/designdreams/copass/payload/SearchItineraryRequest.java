package com.designdreams.copass.payload;

import com.designdreams.copass.bean.User;
import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

public class SearchItineraryRequest {

    @JsonProperty(required = true)
    private User user;

    @JsonProperty(required = true)
    @NotEmpty(message = "Source cannot be empty")
    private String source;

    @JsonProperty(required = true)
    @NotEmpty(message = "destination cannot be empty")
    private String destination;

  //  @DateTimeFormat(pattern = "YYYY-mm-dd")
    private String travelStartDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
