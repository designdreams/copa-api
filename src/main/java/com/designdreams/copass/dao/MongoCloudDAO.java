package com.designdreams.copass.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MongoCloudDAO {

    private static final Logger logger = LogManager.getLogger(DAOConfig.class);

    @Value("${MONGO_DB}")
    String database;

    @Value("${MONGO_COLLECTION}")
    String collection;

    @Autowired
    MongoClient mongoClient;

    @Bean
    public String database() {
        return database;
    }

    @Bean
    public String collection() {
        return collection;
    }

    @Bean
    public boolean mongoCheck() {


        try {

            MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
            BasicDBObject bson = new BasicDBObject();

            logger.info(" Connected to GCP Mongo :: " + mongoCollection.countDocuments());

        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }

        return true;
    }


}
