package com.designdreams.copass.service;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.dao.TripDAOImpl;
import com.designdreams.copass.payload.CreateItineraryRequest;
import com.designdreams.copass.payload.ReadItineraryRequest;
import com.designdreams.copass.payload.ReadItineraryResponse;
import com.designdreams.copass.utils.AppUtils;
import com.designdreams.copass.utils.ResponseUtil;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TripService {

    @Autowired
    TripDAOImpl tripDAO;

    @RequestMapping(
            value = "/createTrip",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrip(@RequestBody CreateItineraryRequest createItineraryRequest,
                                             @RequestHeader Map requestHeader) {

        String travellerId;
        ResponseEntity<String> responseEntity = null;
        String respMsg;
        String traceId;

        try {

            System.out.println(" createItineraryRequest! "+createItineraryRequest.toString());

            traceId = AppUtils.getTraceId(requestHeader.get("request-id"));

            if(null!=(respMsg = ResponseUtil.validate(createItineraryRequest)))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", respMsg);

            travellerId = createItineraryRequest.getTravellerId();

            Trip trip = new Trip();
            trip.setSource(createItineraryRequest.getTrip().getSource());
            trip.setDestination(createItineraryRequest.getTrip().getDestination());
            trip.setTravelStartDate(createItineraryRequest.getTrip().getTravelStartDate());
            trip.setMode(createItineraryRequest.getTrip().getMode());
            trip.setTravellingWith(createItineraryRequest.getTrip().getTravellingWith());
            trip.setTicketBooked(createItineraryRequest.getTrip().isTicketBooked());
            trip.setCanTakePackageInd(createItineraryRequest.getTrip().isCanTakePackageInd());
            trip.setFinalDestination(createItineraryRequest.getTrip().isFinalDestination());
            trip.setDomestic(createItineraryRequest.getTrip().isDomestic());

            if("SUCCESS".equalsIgnoreCase(tripDAO.createTrip(traceId, travellerId, trip))){
                System.out.println(" Successfully created trip !");
                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Successfully created trip!");

            }else{
                System.out.println(" Could not created trip !");
                responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "Failed to create trip!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "Error in creating trip!");

        }

        return responseEntity;

    }

    @PostMapping(
            value = "/readTrip",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> readTrip(@RequestBody ReadItineraryRequest readItineraryRequest) {

        List<Trip> tripList = null;
        String emailId = null;
        ReadItineraryResponse readItineraryResponse = null;
        String readTripResponse = null;
        ResponseEntity<String> responseEntity = null;

        try {

            emailId = readItineraryRequest.getUser().getEmailId();

            tripList = tripDAO.getTripDetailsList(emailId);

            if (tripList.size() > 0) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());
                readItineraryResponse = new ReadItineraryResponse();
                readItineraryResponse.setTravellerId(emailId);
                readItineraryResponse.setTripList(tripList);

                readTripResponse = mapper.writeValueAsString(readItineraryResponse);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, readTripResponse);

            } else {

                responseEntity = ResponseUtil.getResponse(HttpStatus.NOT_FOUND, "Trips Not Found", "Try again later");

            }
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", "Try again later");
        }

        return responseEntity;
    }

    @RequestMapping("/updateTrip")
    public String updateTrip() {

        return "success";
    }


    @RequestMapping("/deleteTrip")
    public String deleteTrip() {

        return "success";
    }

    @RequestMapping("/findTrip")
    public String findTrip() {

        return "success";
    }


}
