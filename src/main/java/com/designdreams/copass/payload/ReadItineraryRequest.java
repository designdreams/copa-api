package com.designdreams.copass.payload;

import com.designdreams.copass.bean.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadItineraryRequest {

    @JsonProperty(required = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
