package com.designdreams.copass.dao;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.utils.AppUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripDAOImpl implements TripDAO {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    String database;

    @Autowired
    String collection;

    @Override
    public String createTrip(String uuid, String emailId, Trip trip) {

        String status = null;

        try {

            List<Trip> tripList = new ArrayList<>();
            tripList.add(trip);

            System.out.println(tripList);
            Document match = new Document();
            match.put("emailId", emailId);

            // TODO change to direct query
            Document doc = mongoClient.getDatabase(database).getCollection(collection).find(match).first();

            if (null == doc || (doc != null && doc.isEmpty())) {
                // user not found.
                throw new RuntimeException("USER_NOT_FOUND");
            }

            Document tripDoc = Document.parse(AppUtil.getGson().toJson(trip));
            Document update = new Document();
            update.put("$push", new Document("trips", tripDoc));

            System.out.println(tripDoc.toJson());
            mongoClient.getDatabase(database).getCollection(collection).updateOne(match, update);

            status = "SUCCESS";

        } catch (RuntimeException e) {
            status = "USER_NOT_FOUND";
            e.printStackTrace();

        } catch (Exception e) {
            status = "FAILURE";
            e.printStackTrace();
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
            e.printStackTrace();
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
        List<Trip> tripList = new ArrayList<>();
        String jsonArray = null;
        List<Document> trips;


        try {
            System.out.println("emailId:: " + emailId);

            Document bson = new Document();
            bson.put("emailId", emailId);
            Document doc = mongoClient.getDatabase(database).getCollection(collection).find(bson).first();

            trips = doc.containsKey("trips") ? doc.getList("trips", Document.class) : null;

            if (trips != null) {
                tripList = toList(AppUtil.getGson().toJson(trips), Trip.class);
            }

            System.out.println(" tripList ===> " + tripList);

        } catch (Exception e) {
            e.printStackTrace();
            tripList = null;
        }

        return tripList;

    }


    @Override
    public List<Trip> searchTripDetailsList(String traceId, final String src, final String dest, String date) {

        String name = null;
        List<Trip> tripList = null;
        List<Trip> filteredTripList = null;

        MongoCursor<Document> docCursor = null;
        try {

            System.out.println("src:: " + src + " dest:: " + dest);

            Document match = new Document();

            match.put("trips.source", src);
            match.put("trips.destination", dest);


            if (null != date) {
                match.put("date", date);
            }

            System.out.println(match.toJson());

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

            System.out.println("All TRIPS " + filteredTripList);

            if (tripList.size() < 1)
                throw new RuntimeException("NOT FOUND");

        } catch (RuntimeException e) {
            System.out.println(" NOT_FOUND :: traceId:: " + traceId);
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filteredTripList;

    }

    public boolean isExactTripMatch(Trip trip, String src, String dest) {
        return (trip != null && trip.getSource().equalsIgnoreCase(src) && trip.getDestination().equalsIgnoreCase(dest));
    }

}