package com.designdreams.copass.service;


import com.designdreams.copass.bean.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Controller
@CrossOrigin(origins = "*")
public class AuthController {

    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final Logger logger = LogManager.getLogger(Auth.class);

    @Autowired
    UserService us;

    @PostMapping("/authcon")
    public ModelAndView authenticated(HttpServletRequest request,
                                      HttpServletResponse response
                                       ) {

        String status = "FAILED";
        ModelAndView mnv = null;
        JsonWebToken.Payload payload = null;
        String uuid = null;
        String idTokenString =null;
        Cookie cook = null;
        HttpHeaders headers = null;

        try {

            logger.info(" Auth service " );

            //idTokenString = request.getHeader("x-app-auth-token");
            idTokenString = request.getParameter("app-token" +
                    "");

            uuid = request.getHeader("x-app-trace-id");

            logger.info(" idTokenString : {}", idTokenString);

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
                name = (name !=null)? name.toUpperCase():"";

                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
                User user;
                boolean registrationStatus = false;

                logger.info("payload :" + payload.getJwtId());
                // Use or store profile information

                mnv = new ModelAndView("home");
                mnv.addObject("message", name);
                mnv.addObject("email", email);
                mnv.addObject("pictureUrl", pictureUrl);
                mnv.addObject("locale", locale);

                logger.info("Auth success for {}", email);


                // check if use already present
                 if(!us.ifUserPresent(uuid, email)){
                     user = new User();
                     user.setEmailId(email);
                     user.setName(name);
                     registrationStatus = us.registerUserFirstTime(uuid, user);

                     logger.info(" First time user {}, registered : {} ", email, registrationStatus);
                 }else{
                     logger.info(" Already registered user : {}",email);
                 }

                cook = new Cookie("app_token",idTokenString);
                //cook.setSecure(true);
                cook.setHttpOnly(true);
                cook.setMaxAge(60*15);

                response.addCookie(cook);

            } else {
                logger.info("Invalid ID token ");

                mnv = new ModelAndView("error");

            }

        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Unknown Exception occurred ", e);
            mnv = new ModelAndView("error");

        }

        return mnv;
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                                      HttpServletResponse response
    ) {

        ModelAndView mnv = null;

        try {

            Cookie cook = null;
            mnv = null;
            mnv = new ModelAndView("index");
            cook = new Cookie("app_token",null);
            cook.setMaxAge(-1);
            response.addCookie(cook);

        } catch (Exception e) {
            e.printStackTrace();
            mnv = new ModelAndView("index");

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
