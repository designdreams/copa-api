package com.designdreams.copass.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadTripRequest {

    @JsonProperty(required = true)
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
