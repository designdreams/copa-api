package com.designdreams.copass.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class Auth {

    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final Logger logger = LogManager.getLogger(Auth.class);

    @PostMapping("/auth")
    public ModelAndView authenticated(
            @RequestBody String request,
            @RequestHeader Map<String, String> headers) {


        String status = "FAILED";
        ModelAndView mnv = null;
        JsonWebToken.Payload payload = null;
        String uuid = null;
        String idTokenString =null;

        try {

            logger.info(" Auth service " + request);

            idTokenString = headers.get("x-app-auth-token");
            uuid = headers.get("x-app-trace-id");

            //logger.info("token " + idTokenString);

            if (null == idTokenString)
                throw new RuntimeException("EMPTY_TOKEN_STRING");

            payload = validateToken(uuid, idTokenString);

            if (payload != null) {

                // Print user identifier
                String userId = payload.getSubject();
                logger.info("User ID: " + userId);

                // Get profile information from payload
                String email = ((GoogleIdToken.Payload) payload).getEmail();
                logger.info("email: " + email);

                boolean emailVerified = Boolean.valueOf(((GoogleIdToken.Payload) payload).getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                logger.info("payload :" + payload.getJwtId());
                // Use or store profile information

                mnv = new ModelAndView("home");
                mnv.addObject("message", name);
                mnv.addObject("pictureUrl", pictureUrl);
                mnv.addObject("locale", locale);

                logger.info("Auth success for {}", email);

            } else {
                logger.info("Invalid ID token ");
            }

        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Unknown Exception occurred ", e);

        }

        return mnv;
    }

    public static JsonWebToken.Payload validateToken(String uuid, String idTokenString) {

        String CLIENT_ID = "579504878975-0l8ngko4cf3nnhp71qqj8hpuiu2e1aik.apps.googleusercontent.com";
        String CLIENT_ID_1 = "579504878975-dg9bab7a53fvhmpet6kuuho4j1e40p8o.apps.googleusercontent.com";
        String CLIENT_ID_2 = "579504878975-89b6fg2ctc46c0aqslrg5b37c234rj5h.apps.googleusercontent.com";

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
