package com.designdreams.copass.utils;

import com.designdreams.copass.dao.UserDAOImpl;
import com.designdreams.copass.payload.CreateTripRequest;
import com.designdreams.copass.payload.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ResponseUtil {

    private static final Logger logger = LogManager.getLogger(ResponseUtil.class);

    public static ResponseEntity<String> getResponse(HttpStatus status, String respCode, String respMessage) {

        ResponseEntity<String> responseEntity = null;

        try {

            if (null != respCode) {

                Response response = new Response();
                response.setRespCode(respCode);
                response.setRespMessage(respMessage);

                ObjectMapper mapper = new ObjectMapper();
                mapper.writer(new DefaultPrettyPrinter());

                logger.info(mapper.writeValueAsString(response));
                responseEntity = new ResponseEntity<>(mapper.writeValueAsString(response), status);

            } else {

                responseEntity = new ResponseEntity<>(respMessage, status);

            }

        } catch (JsonProcessingException e) {
            logger.error(e, e);
        }

        return responseEntity;

    }

    public static String validate(Object createTripRequest) {

        Set errMsgSet = null;
        String errMsg = "unknown error";

        try {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            errMsgSet = validator.validate(createTripRequest);
            errMsg = (null != errMsgSet && !errMsgSet.isEmpty()) ? ((ConstraintViolation) errMsgSet.toArray()[0]).getMessage() : null;

        } catch (Exception e) {
            logger.error(e, e);
        }

        return errMsg;

    }
}
