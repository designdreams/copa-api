package com.designdreams.copass.dao;

import com.designdreams.copass.bean.Alert;
import com.designdreams.copass.bean.Trip;

import java.util.List;

public interface AlertDAO {

    String setupAlert(String uuid,String tripId,String travellerId, boolean alertEnableInd);

    List<Alert> getAlertsList(List<Trip> allTripList);
    List<Trip>  getAllTripList();


    }
