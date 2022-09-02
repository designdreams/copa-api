package com.designdreams.copass.dao;

import com.designdreams.copass.bean.Alert;
import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.utils.AppUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertDAOImpl implements AlertDAO {

    private static final Logger logger = LogManager.getLogger(AlertDAOImpl.class);

    @Autowired
    MongoClient mongoClient;

    @Autowired
    String database;

    @Autowired
    String collection;

    @Override
    public String setupAlert(String uuid, String tripId, String travellerId, boolean alertEnableInd) {






        String status = "SUCCESS";

        return status;
    }


    // get all trips list
    public List<Trip>  getAllTripList(){

        List<Trip> allTripList = new ArrayList<Trip>();
        List<Document> docList = new ArrayList();
        List tmpList = null;

        try {

            Document bson = new Document();

            MongoCursor<Document> docs = mongoClient.getDatabase(database).getCollection(collection).find(bson).iterator();

            while (docs != null && docs.hasNext()) {

                docList = docs.next().getList("trips", Document.class);

                if (docList != null) {

                    tmpList = docList.stream()
                            .map(e -> AppUtil.getGson().fromJson(e.toJson(), Trip.class))
                            .collect(Collectors.toList());
                    allTripList.addAll(tmpList);
                }
            }

            logger.info(" tripListSize:" + allTripList.size() );
            logger.info(" tripList:" + allTripList );



            logger.info("allTripList size"+allTripList.size());
            logger.info("allTripList"+allTripList);

            return allTripList;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e,e);
            return null;
        }
    }


    // get all trips that has alerts turned on!
    public List<Alert> getAlertsList(List<Trip> allTripList){

        List<Trip> alertTrips = new ArrayList<Trip>();
        HashMap<String,Integer> countMap = new HashMap<>();
        List<Alert> alertList =new ArrayList<>();
        List<Document> docList = new ArrayList();
        List tmpList = null;

        try {

            logger.info(" tripListSize:" + allTripList.size() );
            logger.info(" tripList:" + allTripList );

            alertTrips=allTripList.stream().filter(s->s.getActivateAlert()).collect(Collectors.toList());

            logger.info(" AlerttemptripListSize:" + alertTrips.size() );
            logger.info(" AlerttemptripList:" + alertTrips );
            logger.info("AlertList size"+alertList.size());
            logger.info("AlertList"+alertList);

            return alertList;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e,e);
            return null;
        }
    }

    /*// get all trips that has alerts turned on!
    public List<Alert> getAlertsMatchList(List<Trip> allTripList){

        List<Trip> alertTrips = new ArrayList<Trip>();
        HashMap<String,Integer> countMap = new HashMap<>();
        List<Alert> alertList =new ArrayList<>();
        List<Document> docList = new ArrayList();
        List tmpList = null;

        try {

            logger.info(" tripListSize:" + allTripList.size() );
            logger.info(" tripList:" + allTripList );

            alertTrips=allTripList.stream().filter(s->s.getActivateAlert()).collect(Collectors.toList());

            logger.info(" AlerttemptripListSize:" + alertTrips.size() );
            logger.info(" AlerttemptripList:" + alertTrips );

*//*            for(Trip alertTrip :alertTrips){
                int count=0;
                for(Trip allTrips:allTripList){

                    if(alertTrip.getSource().equalsIgnoreCase(allTrips.getSource()) &&
                            alertTrip.getDestination().equalsIgnoreCase(allTrips.getDestination())){
                        count++;
                    }
                }
                if(count>1)
                    countMap.put(alertTrip.getSource()+"-"+alertTrip.getDestination(),count);

            }*//*



*//*            for(Trip trip : alertTrips){

                if(countMap.containsKey((trip.getSource()+'-'+trip.getDestination()))){
                    Alert alert = new Alert();
                    alert.setEmailId(trip.getTravellerId());
                    alert.setSrc(trip.getSource());
                    alert.setDest(trip.getDestination());
                    alert.setMatchCount(countMap.get(trip.getSource()+'-'+trip.getDestination()));
                    alertList.add(alert);
                }
            }*//*

            logger.info("AlertList size"+alertList.size());
            logger.info("AlertList"+alertList);

            return alertList;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e,e);
            return null;
        }
    }*/


}
