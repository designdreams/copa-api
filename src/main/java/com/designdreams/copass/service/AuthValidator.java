package com.designdreams.copass.service;

import com.designdreams.copass.utils.AES;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class AuthValidator {

    @Value("${CLIENT_ID_1}")
    private String clientIdOne;

    @Value("${CLIENT_ID_2}")
    private String clientIdTwo;

    @Value("${CLIENT_ID_3}")
    private String clientIdThree;

    @Value("${AES_TOKEN}")
    private String token;

    private Logger logger = LogManager.getLogger(Auth.class);

    private JacksonFactory jacksonFactory = new JacksonFactory();

    public String getClientIdOne() {
        return clientIdOne;
    }

    public void setClientIdOne(String clientIdOne) {
        this.clientIdOne = clientIdOne;
    }

    public String getClientIdTwo() {
        return clientIdTwo;
    }

    public void setClientIdTwo(String clientIdTwo) {
        this.clientIdTwo = clientIdTwo;
    }

    public String getClientIdThree() {
        return clientIdThree;
    }

    public void setClientIdThree(String clientIdThree) {
        this.clientIdThree = clientIdThree;
    }

    public  JsonWebToken.Payload validateToken(String uuid, String idTokenString) {

        //System.out.println("================>>"+clientIdOne);
        // Encrypt these
        String CLIENT_ID = AES.decrypt(clientIdOne,token);
        String CLIENT_ID_1 = AES.decrypt(clientIdTwo,token);
        String CLIENT_ID_2 = AES.decrypt(clientIdThree,token);

        try {
            if(null == idTokenString)
                return null;

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), jacksonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    .setAudience(Arrays.asList(CLIENT_ID, CLIENT_ID_1, CLIENT_ID_2))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                JsonWebToken.Payload payload = idToken.getPayload();
                return payload;
            } else {
                return null;
            }
        } catch (GeneralSecurityException e) {
            logger.error("Sec Exception occurred ", e);
            e.printStackTrace();

        } catch (Exception e) {
            logger.error("Unknown Exception occurred ", e);
            e.printStackTrace();
        }

        return null;
    }


}
