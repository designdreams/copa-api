package com.designdreams.copass.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trip {

    private String tripId;

    @JsonProperty(required = true)
    @NotEmpty(message = "Valid TravellerId is required")
    @Email(message = "Valid TravellerId is required")
    private String travellerId;

    @JsonProperty(required = true)
    @NotEmpty(message = "source is required")
    @Size(min = 2, max = 3, message = "Invalid Source, Limit to 3 characters")
    private String source;

    @JsonProperty(required = true)
    @NotEmpty(message = "destination is required")
    @Size(min = 2, max = 3, message = "Invalid Destination, Limit to 3 characters")
    private String destination;

    @JsonProperty
    @Size(max = 16, message = "Invalid Date, Limit to 16 characters")
    private String travelStartDate;

    private String travelMonth;

    @JsonProperty
    @Size(max = 10, message = "Invalid mode, Limit to 10 characters")
    private String mode;

    @Size(max = 10, message = "Invalid airways, Limit to 10 characters")
    private String airways;

    @Size(max = 10, message = "Invalid travel with, Limit to 10 characters")
    private String travellingWith;

    private boolean isTicketBooked;

    private boolean canTakePackageInd;

    private boolean isDomestic;

    private boolean isFinalDestination;

    private boolean activateAlert;

    @Size(max = 150, message = "Invalid notes, Limit to 150 characters")
    private String notes;

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    private Date sortDate;

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
        this.notes = notes.replaceAll("[^a-zA-Z0-9 ]", "");;
	}


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

    public String getTravelMonth() {
        return travelMonth;
    }

    public void setTravelMonth(String travelMonth) {
        this.travelMonth = travelMonth;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTravellingWith() {
        return travellingWith;
    }

    public void setTravellingWith(String travellingWith) {
        this.travellingWith = travellingWith;
    }

    public boolean getIsTicketBooked() {
        return isTicketBooked;
    }

    public void setIsTicketBooked(boolean ticketBooked) {
        isTicketBooked = ticketBooked;
    }

    public String getAirways() {
        return airways;
    }

    public void setAirways(String airways) {
        this.airways = airways;
    }

    public boolean isCanTakePackageInd() {
        return canTakePackageInd;
    }

    public void setCanTakePackageInd(boolean canTakePackageInd) {
        this.canTakePackageInd = canTakePackageInd;
    }

    public boolean getIsDomestic() {
        return isDomestic;
    }

    public void setIsDomestic(boolean domestic) {
        this.isDomestic = domestic;
    }

    public boolean getIsFinalDestination() {
        return isFinalDestination;
    }

    public void setIsFinalDestination(boolean finalDestination) {
        this.isFinalDestination = finalDestination;
    }

    public boolean getActivateAlert() {
        return activateAlert;
    }

    public void setActivateAlert(boolean activateAlert) {
        this.activateAlert = activateAlert;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId='" + tripId + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", travelStartDate='" + travelStartDate + '\'' +
                ", travelMonth='" + travelMonth + '\'' +
                ", mode='" + mode + '\'' +
                ", airways='" + airways + '\'' +
                ", travellingWith='" + travellingWith + '\'' +
                ", isTicketBooked=" + isTicketBooked +
                ", isDomestic=" + isDomestic +
                ", canTakePackageInd=" + canTakePackageInd +
                ", isFinalDestination=" + isFinalDestination +
                ", activateAlert=" + activateAlert +
                ", notes=" + notes +
                '}';
    }
}