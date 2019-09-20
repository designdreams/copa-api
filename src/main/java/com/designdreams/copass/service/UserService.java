package com.designdreams.copass.service;

import com.designdreams.copass.bean.User;
import com.designdreams.copass.dao.UserDAOImpl;
import com.designdreams.copass.payload.CreateUserRequest;
import com.designdreams.copass.payload.GetUserRequest;
import com.designdreams.copass.utils.AppUtil;
import com.designdreams.copass.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserService {

    @Autowired
    UserDAOImpl userDAOImpl;

    @PostMapping(value = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody CreateUserRequest createUserRequest) {

        System.out.println("=======" + createUserRequest.toString());
        User user = createUserRequest.getUser();

        System.out.println("=======" + user.toString());

        if (userDAOImpl.createUser("123", user)) {

            return ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Registered user successfully");

        } else {

            return ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "try again later");
        }

    }

    @PostMapping(value="/user/get",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUser(@RequestBody GetUserRequest getUserRequest, @RequestHeader HttpHeaders headers) {

        String response = null;
        String emailId = null;
        String uuid = null;

        uuid = AppUtil.getUuid();

        emailId = getUserRequest.getEmail();

        System.out.println("=======" + emailId);

        response = userDAOImpl.getUser(uuid, emailId);

        if (null != response)
            return ResponseUtil.getResponse(HttpStatus.OK, null, response);
        else
            return ResponseUtil.getResponse(HttpStatus.OK, "USER_NOT_FOUND", "User not registered");

    }


    @PostMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestBody CreateUserRequest createUserRequest) {

        if (userDAOImpl.removeUser("123", createUserRequest.getUser().getEmailId())) {

            return ResponseUtil.getResponse(HttpStatus.OK, "SUCCESS", "Removed user account");

        } else {

            return ResponseUtil.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "FAILURE", "Try again later");
        }

    }
}
