package com.designdreams.copass.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.appengine.repackaged.org.joda.time.DateTimeComparator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.http.client.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class Auth {

    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final Logger logger = LogManager.getLogger(Auth.class);

    public static boolean valiateDate(String date){

        String dateFormat = "yyyy-MM-dd";
        String currentDate = java.time.LocalDate.now().toString();


       if (date !=null && !date.isEmpty() &&
               GenericValidator.isDate(date, dateFormat, true)
       &&  DateTimeComparator.getDateOnlyInstance().compare(date, currentDate) >= 0){

           logger.info("validate date " +GenericValidator.isDate(date, dateFormat, true));
           logger.info("validate -- date " +DateTimeComparator.getDateOnlyInstance().compare(currentDate, date));

           return true;

       }

        // empty date is allowed
        if (date !=null && date.isEmpty()){
            return true;
        }

        return false;
    }
}
