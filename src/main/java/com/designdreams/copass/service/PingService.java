package com.designdreams.copass.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class PingService {

    @RequestMapping("/ping")
    public String ping() {
        return "success";
    }

}
