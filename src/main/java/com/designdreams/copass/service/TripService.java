package com.designdreams.copass.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripService {

    @RequestMapping("/createTrip")
    public String createTrip(){

        return "success";
    }

    @RequestMapping("/readTrip")
    public String readTrip(){

        return "success";
    }

    @RequestMapping("/updateTrip")
    public String updateTrip(){

        return "success";
    }


    @RequestMapping("/deleteTrip")
    public String deleteTrip(){

        return "success";
    }

    @RequestMapping("/findTrip")
    public String findTrip(){

        return "success";
    }


}
