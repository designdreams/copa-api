package com.designdreams.copass.service;

import com.designdreams.copass.dao.TravellerDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TravellerService {

    @Autowired
    TravellerDAOImpl travellerDAO;

    @RequestMapping("/readTravellerInfo")
    public String readTravellerInfo(@RequestParam String id) {

        String email;

        System.out.println(" Read Traveller info " + id);

        //travellerDAO = (TravellerDAOImpl) AppStaticFactory.getContext().getBean("travellerDAO");
        email = travellerDAO.getTravellerInfo(Integer.valueOf(id));

        System.out.println(" Traveller info " + email);
        return email;
    }
}
