package com.designdreams.copass.service;


import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class Auth {

    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @PostMapping(
            value = "/auth",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(
            @RequestBody String request,
            @RequestHeader Map<String, String> headers) {

        String CLIENT_ID = "579504878975-0l8ngko4cf3nnhp71qqj8hpuiu2e1aik";
        String status = "FAILED";

        try {
            System.out.println(" Auth service " + request);
            String idTokenString = headers.get("auth_token");

            System.out.println(idTokenString);

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                JsonWebToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = ((GoogleIdToken.Payload) payload).getEmail();
                System.out.println("email: " + email);

                boolean emailVerified = Boolean.valueOf(((GoogleIdToken.Payload) payload).getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                System.out.println("payload :" + payload.getJwtId());
                // Use or store profile information
                // ...

                status = "SUCCESS";

            } else {
                System.out.println("Invalid ID token.");
            }

        } catch (GeneralSecurityException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return status;
    }


    @PostMapping("/authorize")
    public ModelAndView authenticated(
            @RequestBody String request,
            @RequestHeader Map<String, String> headers) {

        String CLIENT_ID = "579504878975-0l8ngko4cf3nnhp71qqj8hpuiu2e1aik";
        String status = "FAILED";
        ModelAndView mnv = null;

        try {

            System.out.println(" Auth service " + request);

            String idTokenString = headers.get("auth_token");

            System.out.println("token" + idTokenString);

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            if (null == idTokenString)
                throw new RuntimeException("EMPTY_TOKEN_STRING");

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                JsonWebToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = ((GoogleIdToken.Payload) payload).getEmail();
                System.out.println("email: " + email);

                boolean emailVerified = Boolean.valueOf(((GoogleIdToken.Payload) payload).getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                System.out.println("payload :" + payload.getJwtId());
                // Use or store profile information

                status = "SUCCESS";

                mnv = new ModelAndView("home");
                mnv.addObject("message", name);

            } else {
                System.out.println("Invalid ID token.");
            }

        } catch (GeneralSecurityException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return mnv;
    }

}
