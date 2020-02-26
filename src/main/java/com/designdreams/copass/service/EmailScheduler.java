package com.designdreams.copass.service;

import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.dao.TripDAOImpl;
import com.designdreams.copass.utils.AppUtil;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailScheduler {

    private static final Logger logger = LogManager.getLogger(EmailScheduler.class);

    @Autowired
    MongoClient mongoClient;

    @Autowired
    String database;

    @Autowired
    String collection;


    public List<Trip> getAllEmail(){

    List<Trip> tripList = null;
    List<Document> trips = null;

    try{


    Document bson = new Document();
    bson.put("travelStartDate", BasicDBObjectBuilder.start("$gte", LocalDate.now()));

    Document doc = mongoClient.getDatabase(database).getCollection(collection).find(bson).first();

    if (null != doc)
        trips = doc.containsKey("trips") ? doc.getList("trips", Document.class) : null;

    if (trips != null) {
        tripList = TripDAOImpl.toList(AppUtil.getGson().toJson(trips), Trip.class);
    }
    }catch (Exception e){
        logger.error(e,e);
        tripList = null;
    }
    logger.info(" tripList:" + tripList);
    return tripList;
}


public Map filterTravelerDetails(){

    List<Trip> tripList = getAllEmail();

     Map<String, List<String>> collect= tripList.stream().collect(Collectors.groupingBy(Trip::getDestination,Collectors.mapping(Trip::getTravellerId,Collectors.toList())));

    logger.info(collect);

    return collect;

}
//0 0/1 * 1/1 * ? * --every min
    //"0 0 0 1/1 * ? *" -every day
@Scheduled(cron="0 0/1 * 1/1 * ? *")
public void callEmail(){


    Map<String, List<String>> emailRecipient = filterTravelerDetails();

        emailRecipient.forEach((k,v) -> v.forEach(t -> new SendEmail().email(t,k)));


}


}
