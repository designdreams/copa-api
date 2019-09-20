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
    public List<Trip> searchTripDetailsList(String traceId, String src, String dest, String date) {

        String name = null;
        List<Trip> tripList = null;
        MongoCursor<Document> doc = null;
        try {

            System.out.println("src:: " + src + " dest:: " + dest);
            Document query = new Document();

            Document bson = new Document();
            Document match = new Document();

            match.put("source", src);
            match.put("destination", dest);

            // {awards: {$elemMatch: {award:'National Medal', year:1975}

            if (null != date) {
                match.put("date", date);
            }

            bson.put("$elemMatch", match);
            query.put("trips", bson);

            System.out.printf(query.toJson());

            doc = mongoClient.getDatabase(database).getCollection(collection).find(query).iterator();

            tripList = new ArrayList<>();

            // {trips: {$elemMatch: {source : "CCC" destination : "ddd"}}}

            String json = null;
            while (doc != null && doc.hasNext()) {

                json = doc.next().toJson();
                tripList.add(AppUtil.getGson().fromJson(json, Trip.class));

                System.out.println("TRIP " + json);
            }

            if (tripList.size() < 1)
                throw new RuntimeException("NOT FOUND");

        } catch (RuntimeException e) {
            name = "NOT_FOUND";
            System.out.println(" NOT_FOUND :: traceId:: " + traceId);

        } catch (Exception e) {
            name = "DB_ERROR";
            e.printStackTrace();
        }

        return tripList;

    }

}