package com.designdreams.copass.dao;

import com.designdreams.copass.bean.User;
import com.designdreams.copass.utils.AppUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    MongoClient mongoClient;

    @Autowired
    String database;

    @Autowired
    String collection;

    public boolean createUser(String uuid, User user) {

        Document doc = new Document();

        try {
            doc.put("emailId", user.getEmailId());
            doc.put("user", Document.parse(AppUtil.getGson().toJson(user)));
            mongoClient.getDatabase(database).getCollection(collection).insertOne(doc);
            return true;

        }  catch (MongoWriteException e) {
            // Duplicate key. Email already registered
            if(11000 == e.getError().getCode()){
                return true;
            }
            logger.error(e, e);

        }  catch (Exception e) {
            logger.error(e, e);
        }

        return false;
    }

    public String getUser(String uuid, String emailId) {

        Document doc = null;
        String response = null;

        try {
            Document match = new Document();
            match.put("emailId", emailId);

            doc = mongoClient.getDatabase(database).getCollection(collection).find(match).first();

            logger.info("{} - {}",uuid, doc.toJson());
            response = doc.get("user").toString();

        } catch (Exception e) {
            logger.error(e, e);
        }

        return response;
    }

    public boolean removeUser(String uuid, String emailId) {

        long count = 0L;

        try {
            Document match = new Document();
            match.put("emailId", emailId);

            count = mongoClient.getDatabase(database).getCollection(collection).deleteMany(match).getDeletedCount();

            if(count > 0) return true;

        } catch (Exception e) {
            logger.error("{} : {} ", e, uuid, e);
        }

        return false;
    }


}
