package com.designdreams.copass.service;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.dao.TripDAOImpl;
import com.designdreams.copass.payload.*;
import com.designdreams.copass.utils.ResponseUtil;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TripService {

    private static final Logger logger = LogManager.getLogger(TripService.class);

    @Autowired
    TripDAOImpl tripDAO;

    @RequestMapping(
            value = "/createTrip",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrip(@RequestBody CreateTripRequest createTripRequest,
                                             @RequestHeader Map<String, String> headers,
                                             @CookieValue(required=false, value = "app_token") String token) {

        String travellerId;
        ResponseEntity<String> responseEntity = null;
        String respMsg;
        String traceId;
        String status = null;
        String uuid = null;

        try {

            logger.info(" createTripRequest! " + createTripRequest.toString());

            uuid = headers.get("x-app-trace-id");

            if( null == token)
                token = headers.get("x-app-auth-token");


            if(StringUtils.isEmpty(uuid))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

            if (null != (respMsg = ResponseUtil.validate(createTripRequest)))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", respMsg);

            if(null == token || null == Auth.validateToken(uuid,token ))
                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Login failed");

            Trip trip = createTripRequest.getTrip();
            trip.setSource(trip.getSource().toUpperCase());
            trip.setDestination(trip.getDestination().toUpperCase());

            travellerId = trip.getTravellerId();

            status = tripDAO.createTrip(uuid, travellerId, trip);

            if ("SUCCESS".equalsIgnoreCase(status)) {
                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Successfully created trip!");

            } else if ("USER_NOT_REGISTERED".equalsIgnoreCase(status)) {
                responseEntity = ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, status, "User not registered!");

            } else if (status.contains("DUPLICATE_TRIP")) {
                responseEntity = ResponseUtil.getResponse(HttpStatus.CONFLICT, status, "You have a trip already on this SRC,DEST or DATE!");

            } else {
                responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, status, "Failed to create trip!");

            }

        } catch (Exception e) {
            logger.error(e, e);
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "Error in creating trip!");

        }

        return responseEntity;

    }

    @PostMapping(
            value = "/readTrip",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> readTrip(
            @RequestBody ReadTripRequest readItineraryRequest,
            @RequestHeader Map<String, String> headers,
            @CookieValue(required=false, value = "app_token") String token) {

        List<Trip> tripList = null;
        List<Trip> finaltripList = new ArrayList<Trip>();
        String emailId = null;
        ReadTripResponse readItineraryResponse = null;
        String readTripResponse = null;
        ResponseEntity<String> responseEntity = null;
        String uuid = null;
        //String token = null;
        JsonWebToken.Payload payload = null;
        String emailFromToken = "";

        try {

            logger.info("token {}",token);

            uuid = headers.get("x-app-trace-id");

            if( null == token)
            token = headers.get("x-app-auth-token");

            if(StringUtils.isEmpty(uuid))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

            if(null == token || null == (payload = Auth.validateToken(uuid,token )))
                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Login failed");

            // authorize user based n email id. he should not see others trips by emailID

            emailId = readItineraryRequest.getUserId();

            payload = Auth.validateToken(uuid, token);

            if(null!=payload)
                emailFromToken = ((GoogleIdToken.Payload) payload).getEmail();
            else
                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Login failed");
//             TODO - uncomment for prod
            if(!emailId.equalsIgnoreCase(emailFromToken)){
                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "user with this email account is not authorized");
            }

            tripList = tripDAO.getTripDetailsList(emailId);

            if (null == tripList) {

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "NO_TRIPS_FOUND", "No Trips found for the user");

            } else if (tripList.size() == 0) {

                readItineraryResponse = new ReadTripResponse();
                readItineraryResponse.setTripList(tripList);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "NO_TRIPS_FOUND", "No Trips found for the user");

                //responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, readTripResponse);


            } else {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());
                readItineraryResponse = new ReadTripResponse();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Iterator it=tripList.iterator();
                while (it.hasNext()){
                    LinkedTreeMap ltm= (LinkedTreeMap) it.next();
                    Trip t=new Trip();
                    t.setTravellerId( (String)ltm.get("travellerId"));
                    t.setSource((String)ltm.get("source"));
                    t.setDestination((String)ltm.get("destination"));
                    t.setTravelStartDate((String)ltm.get("travelStartDate"));
                    t.setMode((String)ltm.get("mode"));
                    t.setAirways((String)ltm.get("airways"));
                    t.setTravellingWith((String)ltm.get("travellingWith"));
                    t.setTicketBooked(ltm.get("isTicketBooked").equals("true"));
                    t.setDomestic(ltm.get("isDomestic").equals("true"));
                    t.setCanTakePackageInd(ltm.get("canTakePackageInd").equals("true"));
                    t.setFinalDestination(ltm.get("isFinalDestination").equals("true"));
                    //t.setSortDate(t.getTravelStartDate()!=null && t.getTravelStartDate()!=""?format.parse(t.getTravelStartDate()):null);
                    finaltripList.add(t);

                }

              // finaltripList.sort(Comparator.comparing(Trip::getSortDate).reversed());

                readItineraryResponse.setTripList(finaltripList);


                //Collections.sort(readItineraryResponse.getTripList().,new MyComparator());
               // readItineraryResponse.getTripList().stream().sorted(Comparator.comparing(Trip::getSource).thenComparing(Trip::getTravelStartDate)).collect(Collectors.toList());

                readTripResponse = mapper.writeValueAsString(readItineraryResponse);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, readTripResponse);

            }
        } catch (Exception e) {
            logger.error( e, e);
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Try again later");
        }

        return responseEntity;
    }

    @RequestMapping("/updateTrip")
    public String updateTrip() {

        return "success";
    }


    @RequestMapping(
            value = "/deleteTrip",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTrip(
            @RequestBody DeleteTripRequest deleteTripRequest,
            @RequestHeader Map<String, String> headers,
            @CookieValue(required=false, value = "app_token") String token) {

        List<Trip> tripList = null;
        String emailId = null;
        String searchItineraryResponseStr = null;
        ResponseEntity<String> responseEntity = null;
        String source = null;
        String destination = null;
        String startDate = null;
        String traceId = null;
        String uuid = null;

        try {

            uuid = headers.get("x-app-trace-id");

            if( null == token)
                token = headers.get("x-app-auth-token");

            if(StringUtils.isEmpty(uuid))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

            if(null == token || null == Auth.validateToken(uuid,token ))
                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Login failed");


            emailId = deleteTripRequest.getUserId();
            source = deleteTripRequest.getSource().toUpperCase();
            destination = deleteTripRequest.getDestination().toUpperCase();
            startDate = deleteTripRequest.getTravelStartDate();

            logger.info(" delete trip by user :: " + deleteTripRequest.toString());

            // tripList = tripDAO.searchTripDetailsList(traceId, source, destination, startDate);


            if (tripDAO.deleteTrip(traceId, emailId, source, destination, startDate)) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Successfully deleted trip!");

            } else {
                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "NO_TRIPS_FOUND", "No trips found for the input");
            }

        } catch (Exception e) {
            logger.error(e, e);
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", "Try again later");
        }

        return responseEntity;
    }

    @RequestMapping(value = "/findTrip", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findTrip(
            @RequestBody SearchTripRequest searchItineraryRequest,
            @RequestHeader Map<String, String> headers,
            @CookieValue(required=false, value = "app_token") String token) {

        List<Trip> tripList = null;
        String emailId = null;
        String searchItineraryResponseStr = null;
        ResponseEntity<String> responseEntity = null;
        String source = null;
        String destination = null;
        String startDate = null;
        String traceId = null;
        String uuid = null;

        try {

            uuid = headers.get("x-app-trace-id");

            if( null == token)
                token = headers.get("x-app-auth-token");

            if(StringUtils.isEmpty(uuid))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

            if(null == token || null == Auth.validateToken(uuid,token ))
                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Login failed");


            emailId = searchItineraryRequest.getUserId();
            source = searchItineraryRequest.getSource().toUpperCase();
            destination = searchItineraryRequest.getDestination().toUpperCase();
            startDate = searchItineraryRequest.getTravelStartDate();

            logger.info(" searched by user :: " + emailId);

            tripList = tripDAO.searchTripDetailsList(traceId, source, destination, startDate);

            if (null != tripList && tripList.size() > 0) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());

                SearchTripResponse searchItineraryResponse = new SearchTripResponse();
                searchItineraryResponse.setTripList(tripList);

                searchItineraryResponseStr = mapper.writeValueAsString(searchItineraryResponse);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, searchItineraryResponseStr);

            } else {

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "NO_TRIPS_FOUND", "No trips found for the input");

            }
        } catch (Exception e) {
            logger.error(e, e);
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", "Try again later");
        }

        return responseEntity;
    }


    @RequestMapping(value = "/findTripOpen", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findTripOpen(
            @RequestBody SearchTripRequest searchItineraryRequest,
            @RequestHeader Map<String, String> headers,
            @CookieValue(required=false, value = "app_token") String token) {

        List<Trip> tripList = null;
        String emailId = null;
        String searchOpenItineraryResponseStr = null;
        ResponseEntity<String> responseEntity = null;
        String source = null;
        String destination = null;
        String startDate = null;
        String traceId = null;
        String uuid = null;

        try {

            uuid = headers.get("x-app-trace-id");

            if( null == token)
                token = headers.get("x-app-auth-token");

            if(StringUtils.isEmpty(uuid))
                return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

//            if(null == token || null == Auth.validateToken(uuid,token ))
//                return ResponseUtil.getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Login failed");


            emailId = searchItineraryRequest.getUserId();
            source = searchItineraryRequest.getSource().toUpperCase();
            destination = searchItineraryRequest.getDestination().toUpperCase();
            startDate = searchItineraryRequest.getTravelStartDate();

            logger.info(" searched by user :: " + emailId);

            tripList = tripDAO.searchTripDetailsList(traceId, source, destination, startDate);

            if (null != tripList && tripList.size() > 0) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());

                SearchOpenTripResponse searchOpenItineraryResponse = new SearchOpenTripResponse();
                searchOpenItineraryResponse.setCount(tripList.size());

                searchOpenItineraryResponseStr = mapper.writeValueAsString(searchOpenItineraryResponse);

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, null, searchOpenItineraryResponseStr);

            } else {

                responseEntity = ResponseUtil.getResponse(HttpStatus.OK, "NO_TRIPS_FOUND", "No trips found for the input");

            }
        } catch (Exception e) {
            logger.error(e, e);
            responseEntity = ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", "Try again later");
        }

        return responseEntity;
    }


}
