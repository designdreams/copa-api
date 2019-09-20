package com.designdreams.copass.service;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.dao.TripDAOImpl;
import com.designdreams.copass.payload.*;
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
@CrossOrigin
public class TripService {

    @Autowired
    TripDAOImpl tripDAO;

    @RequestMapping(
            value = "/createTrip",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrip(@RequestBody CreateTripRequest createTripRequest,
                                             @RequestHeader Map requestHeader) {

        String travellerId;
        ResponseEntity<String> responseEntity = null;
        String respMsg;
        String traceId;

        try {

            System.out.println(" createTripRequest! " + createTripRequest.toString());

            traceId = AppUtils.getTraceId(requestHeader.get("request-id"));

            if (null != (respMsg = ResponseUtil.validate(createTripRequest)))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", respMsg);

            Trip trip = createTripRequest.getTrip();

            travellerId = trip.getTravellerId();

            if ("SUCCESS".equalsIgnoreCase(tripDAO.createTrip(traceId, travellerId, trip))) {
                System.out.println(" Successfully created trip !");
                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Successfully created trip!");

            } else if ("USER_NOT_FOUND".equalsIgnoreCase(tripDAO.createTrip(traceId, travellerId, trip))) {
                System.out.println(" user not found to created trip !");
                responseEntity = ResponseUtil.getResponse(HttpStatus.FORBIDDEN, "FAILURE", "User not registered to created trip!");

            } else {
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
    public ResponseEntity<String> readTrip(@RequestBody ReadTripRequest readItineraryRequest) {

        List<Trip> tripList = null;
        String emailId = null;
        ReadTripResponse readItineraryResponse = null;
        String readTripResponse = null;
        ResponseEntity<String> responseEntity = null;

        try {

            emailId = readItineraryRequest.getUserId();

            tripList = tripDAO.getTripDetailsList(emailId);

            if (null == tripList) {

                responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_FETCH_ERROR", "Try again later");

            } else if (tripList.size() == 0) {

                // responseEntity = ResponseUtil.getResponse(HttpStatus.NOT_FOUND, "TRIPS_NOT_FOUND", "No trips registered for email");

                readItineraryResponse = new ReadTripResponse();
                readItineraryResponse.setTripList(tripList);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, readTripResponse);


            } else {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());
                readItineraryResponse = new ReadTripResponse();
                readItineraryResponse.setTripList(tripList);

                readTripResponse = mapper.writeValueAsString(readItineraryResponse);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, readTripResponse);

            }
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Try again later");
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

    @RequestMapping(value = "/findTrip", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findTrip(@RequestBody SearchTripRequest searchItineraryRequest) {

        List<Trip> tripList = null;
        String emailId = null;
        String searchItineraryResponseStr = null;
        ResponseEntity<String> responseEntity = null;
        String source = null;
        String destination = null;
        String startDate = null;
        String traceId = null;

        try {

            emailId = searchItineraryRequest.getUserId();
            source = searchItineraryRequest.getSource();
            destination = searchItineraryRequest.getDestination();
            startDate = searchItineraryRequest.getTravelStartDate();

            System.out.println(" searched by user :: " + emailId);

            tripList = tripDAO.searchTripDetailsList(traceId, source, destination, startDate);

            if (null != tripList && tripList.size() > 0) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());

                SearchTripResponse searchItineraryResponse = new SearchTripResponse();
                searchItineraryResponse.setTripList(tripList);

                searchItineraryResponseStr = mapper.writeValueAsString(searchItineraryResponse);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, searchItineraryResponseStr);

            } else {

                responseEntity = ResponseUtil.getResponse(HttpStatus.NOT_FOUND, "Trips Not Found", "Try again later");

            }
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", "Try again later");
        }

        return responseEntity;
    }


}
