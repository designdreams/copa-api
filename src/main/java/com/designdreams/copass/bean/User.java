package com.designdreams.copass.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty(required = true)
    @Email(message = "Valid userId is required")
    private String emailId;

    @JsonIgnore
    @Size(max = 10, message = "Limit Name to 30 characters")
    private String name;

    @JsonIgnore
    @Size(max = 1, message = "Limit Gender to 1 characters")
    private String gender;

    @JsonIgnore
    @Range(min = 15, max = 100, message = "Should be above 15 and below 100")
    private String age;

    @JsonIgnore
    @Size(max = 10, message = "Limit contact number to 10 numbers only")
    private String contactNo;

    @JsonIgnore
    @Size(max = 300, message = "Limit about me to 300 numbers only")
    private String aboutMe;

    @JsonIgnore
    private Trip[] trips;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
