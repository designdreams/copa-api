package com.designdreams.copass.service;

import com.designdreams.copass.bean.User;
import com.designdreams.copass.dao.UserDAOImpl;
import com.designdreams.copass.payload.CreateUserRequest;
import com.designdreams.copass.payload.DeleteUserRequest;
import com.designdreams.copass.payload.GetUserRequest;
import com.designdreams.copass.utils.ResponseUtil;
import com.mongodb.WriteConcernException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserDAOImpl userDAOImpl;

    @PostMapping(value = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(
            @RequestBody CreateUserRequest createUserRequest,
            @RequestHeader Map<String, String> headers) {

        String uuid = null;
        uuid = headers.get("x-app-trace-id");

        if(StringUtils.isEmpty(uuid))
            return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

        try {

            logger.info(createUserRequest.toString());

            User user = createUserRequest.getUser();

            if (userDAOImpl.createUser("123", user)) {

                return ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Registered user successfully");

            } else {

                return ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "try again later");
            }

        } catch (WriteConcernException e) {
            logger.error(e,e);
            return ResponseUtil.getResponse(HttpStatus.OK, "ALREADY_ENROLLED", "Email already enrolled");

        } catch (Exception e) {
            logger.error(e,e);
            return ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "try again later");
        }

    }

    @PostMapping(value="/user/get",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUser(
            @RequestBody GetUserRequest getUserRequest,
            @RequestHeader Map<String, String> headers) {

        String response = null;
        String emailId = null;
        String uuid = null;
        uuid = headers.get("x-app-trace-id");

        if(StringUtils.isEmpty(uuid))
            return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

        try {
            logger.info(getUserRequest.toString());

            emailId = getUserRequest.getEmail();

            response = userDAOImpl.getUser(uuid, emailId);

            if (null != response)
                return ResponseUtil.getResponse(HttpStatus.OK, null, response);
            else
                return ResponseUtil.getResponse(HttpStatus.OK, "USER_NOT_FOUND", "User not registered");

        } catch (Exception e) {
            logger.error(e,e);
            return ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "try again later");
        }

    }


    @PostMapping("/user/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody DeleteUserRequest deleteUserRequest,
            @RequestHeader Map<String, String> headers) {

        String uuid = null;
        uuid = headers.get("x-app-trace-id");

        if(StringUtils.isEmpty(uuid))
            return ResponseUtil.getResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Missing header[x-app-trace-id]");

        if (userDAOImpl.removeUser(uuid, deleteUserRequest.getUserId())) {

            return ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Removed user account");

        } else {

            return ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "Try again later");
        }

    }
}
