package com.designdreams.copass.dao;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.utils.AppUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripDAOImpl implements TripDAO {

    private static final Logger logger = LogManager.getLogger(TripDAOImpl.class);

    @Autowired
    MongoClient mongoClient;

    @Autowired
    String database;

    @Autowired
    String collection;

    @Override
    public String createTrip(String uuid, String emailId, Trip trip) {

        String status = "COULD_NOT_CREATE_TRIP";
        List<Document> trips = null;
        long tripCount = 0;
        long sameDayTripCount = 0;

        try {

            List<Trip> tripList = new ArrayList<>();
            tripList.add(trip);

            logger.info(tripList);
            Document match = new Document();
            match.put("emailId", emailId);

            Document doc = mongoClient.getDatabase(database).getCollection(collection).find(match).first();

            if (null == doc || (doc != null && doc.isEmpty())) {
                status = "USER_NOT_REGISTERED";
                return status;

            } else {

                // check if trip already present

                trips = doc.containsKey("trips") ? doc.getList("trips", Document.class) : null;

                if (null != trips) {

                    for (Document t : trips) {

                        if (t.get("source").toString().equalsIgnoreCase(trip.getSource()) &&
                                t.get("destination").toString().equalsIgnoreCase(trip.getDestination())
                                && t.get("travelStartDate").toString().equalsIgnoreCase(trip.getTravelStartDate()))
                            sameDayTripCount++;
                    }

                    if (sameDayTripCount > 0) {
                        status = "INVALID_SAME_DAY_DUPLICATE_TRIP";
                        return status;
                    }


                    for (Document t : trips) {

                        if (t.get("source").toString().equalsIgnoreCase(trip.getSource())
                                && t.get("destination").toString().equalsIgnoreCase(trip.getDestination()))
                            tripCount++;
                    }


                    if (tripCount > 3) {
                        status = "DUPLICATE_TRIP_PLACE_MAX_3";
                        return status;
                    }

                }
            }

            Document tripDoc = Document.parse(AppUtil.getGson().toJson(trip));
            Document update = new Document();
            update.put("$push", new Document("trips", tripDoc));

            logger.info(tripDoc.toJson());
            mongoClient.getDatabase(database).getCollection(collection).updateOne(match, update);

            status = "SUCCESS";

        } catch (Exception e) {

            status = "FAILURE";
            logger.error(e, e);
        }

        return status;
    }

    @Override
    public String getTripDetails(String uuid, String emailId, String tripId) {

        String tripJson = null;

        try {

            Document bson = new Document();
            bson.put("emailId", emailId);
            bson.put("tripId", tripId);

            // this should return trip document inside array
            Document trip = mongoClient.getDatabase(database).getCollection(collection).find(bson).first();
            tripJson = trip.toJson();

        } catch (EmptyResultDataAccessException e) {
            tripJson = "NOT_FOUND";

        } catch (DataAccessException e) {
            tripJson = "DB_ERROR";
            logger.error(e,e);
        }

        return tripJson;

    }


    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    @Override
    public List<Trip> getTripDetailsList(String emailId) {

        String status = null;
        List<Trip> tripList = null;
        String jsonArray = null;
        List<Document> trips = null;


        try {
            //System.out.println("emailId:: " + emailId);

            Document bson = new Document();
            bson.put("emailId", emailId);
            Document doc = mongoClient.getDatabase(database).getCollection(collection).find(bson).first();

            if (null != doc)
                trips = doc.containsKey("trips") ? doc.getList("trips", Document.class) : null;

            if (trips != null) {
                tripList = toList(AppUtil.getGson().toJson(trips), Trip.class);
            }

            logger.info(" tripList:" + tripList);

        } catch (Exception e) {
            logger.error(e,e);
            tripList = null;
        }

        return tripList;

    }


    //@Override
    public boolean deleteTrip(String traceId, String emailId, final String src, final String dest, String date){


        List list = null;
        String name = null;
        List<Trip> tripList = null;
        List<Trip> filteredTripList = null;

        MongoCursor<Document> docCursor = null;

        try {

            list = getTripDetailsList(emailId);

            if(list != null && list.size() > 0){
                // dlte trip

                Bson bson = new BsonDocument();


                logger.info("{} : src:: {} dest:: {} date ::{}", traceId, src, dest,date);

                Document query = new Document();

                Document find = new Document();
                find.put("emailId", emailId);

                Document match = new Document();

                match.put("source", src);
                match.put("destination", dest);
                match.put("travelStartDate", date);


                Document pullDoc = new Document();
                pullDoc.put("trips",match);

                query.put("$pull",pullDoc);

//                    if (null != date && !date.isEmpty()) {
//                        match.put("trips.travelStartDate", date);
//                    }

                logger.info(query.toJson());
                logger.info(find.toJson());

                mongoClient.getDatabase(database).getCollection(collection).findOneAndUpdate(find, query);

                logger.info("deleted.......");

            }

        } catch (Exception e) {

            logger.error(e, e);

        }

        return true;
    }

    @Override
    public List<Trip> searchTripDetailsList(String traceId, final String src, final String dest, String date) {

        String name = null;
        List<Trip> tripList = null;
        List<Trip> filteredTripList = null;

        MongoCursor<Document> docCursor = null;
        try {

            logger.info("{} : src:: {} dest:: {}", traceId, src, dest);

            Document match = new Document();

            match.put("trips.source", src);
            match.put("trips.destination", dest);


            if (null != date && !date.isEmpty()) {
                match.put("trips.travelStartDate", date);
            }

            logger.info(match.toJson());

            docCursor = mongoClient.getDatabase(database).getCollection(collection).find(match).iterator();

            //{trips.source : "CCC",trips.destination : "ddd"}

            tripList = new ArrayList();
            List<Document> docList = new ArrayList();

            List tmpList = null;

            while (docCursor != null && docCursor.hasNext()) {

                docList = docCursor.next().getList("trips", Document.class);

                if (docList != null) {

                    tmpList = docList.stream()
                            .map(e -> AppUtil.getGson().fromJson(e.toJson(), Trip.class))
                            .collect(Collectors.toList());

                    tripList.addAll(tmpList);

                }

            }

            // filter trips
            filteredTripList = tripList.stream()
                    .filter(trip -> isExactTripMatch(trip, src, dest))
                    .collect(Collectors.toList());

            logger.debug("All TRIPS " + filteredTripList);

        } catch (Exception e) {

            logger.error(e, e);

        }

        return filteredTripList;

    }

    public boolean isExactTripMatch(Trip trip, String src, String dest) {
        return (trip != null && trip.getSource().equalsIgnoreCase(src) && trip.getDestination().equalsIgnoreCase(dest));
    }

}