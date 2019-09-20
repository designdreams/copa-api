package com.designdreams.copass.dao;

import com.designdreams.copass.bean.User;
import com.designdreams.copass.utils.AppUtil;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {

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

        } catch (Exception e) {
            e.printStackTrace();
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

            System.out.println(doc.toJson());
            response = doc.get("user").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public boolean removeUser(String uuid, String emailId) {

        long count = 0L;

        try {
            Document match = new Document();
            match.put("emailId", emailId);

            count = mongoClient.getDatabase(database).getCollection(collection).deleteMany(match).getDeletedCount();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
