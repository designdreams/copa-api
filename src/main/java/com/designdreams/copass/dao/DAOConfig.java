package com.designdreams.copass.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DAOConfig {

    @Value("${MONGO_CON_URI}")
    private String connectionUri;

    @Bean
    public MongoClient getMongoClient() {

        MongoClient mongoClient =null;

            try {
                MongoClientURI uri = new MongoClientURI(connectionUri);
                mongoClient = new MongoClient(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return mongoClient;
    }

}
