package com.designdreams.copass.dao;

import com.designdreams.copass.CopassApp;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DAOConfig {

    private static final Logger logger = LogManager.getLogger(DAOConfig.class);

    @Value("${MONGO_CON_URI}")
    private String connectionUri;

    @Bean
    public MongoClient getMongoClient() {

        MongoClient mongoClient =null;

            try {
                MongoClientURI uri = new MongoClientURI(connectionUri);
                mongoClient = new MongoClient(uri);
            } catch (Exception e) {
                logger.error(e, e);
            }

        return mongoClient;
    }

}
