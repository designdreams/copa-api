package com.designdreams.copass.utils;

import java.util.UUID;

public class AppUtils {

    public  static String getMonthFromdate(String date){
        //TODO
        return "JAN";
    }


    public  static String getTraceId(Object reqId){
        return null!=reqId?(String)reqId:UUID.randomUUID().toString();
    }
}
