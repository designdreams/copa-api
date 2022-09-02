package com.designdreams.copass.service;

import com.designdreams.copass.bean.Alert;
import com.designdreams.copass.bean.Trip;
import com.designdreams.copass.dao.AlertDAO;
import com.designdreams.copass.dao.TripDAO;
import com.designdreams.copass.dao.TripDAOImpl;
import com.designdreams.copass.utils.AppUtil;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmailScheduler {

    private static final Logger logger = LogManager.getLogger(EmailScheduler.class);

    @Autowired
    MongoClient mongoClient;

    @Autowired
    String database;

    @Autowired
    String collection;

   // @Autowired
   // SendEmail sendEmail;

    @Autowired
    AlertDAO alertDAO;

    //@Scheduled(cron = "0/15 * * * * *")
    private List<Alert> getAlertsList() {

        List<Alert> alertList;

        System.out.println("=============================");

        logger.info("SENDING EMAIL EVERY 15 mins");

        // get all alerts from alerts collection payanam-alerts

        // for each alerts check if there is any valid travel record match with src and destination.

        // if present, add to list.
        return alertDAO.getAlertsList(alertDAO.getAllTripList());
        //return new ArrayList<>();

}


   //@Scheduled(cron = "0 0/1 * * * *")
//    public void pushEmails() {
//
//        logger.info("SENDING EMAIL EVERY 15 mins");
//
//        List<Alert> alertList = getAlertsList();
//
//        alertList.forEach(alert -> sendEmail.email(alert.getEmailId(), alert.getSrc(), alert.getDest(),alert.getMatchCount()));
//
//
//
//    }


}
