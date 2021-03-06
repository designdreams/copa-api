package com.designdreams.copass.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AppUtil {

    private static Gson gson = new Gson();

    public static Gson getGson(){
        return gson;
    }

    public static String getUuid(){
        return UUID.randomUUID().toString();
    }

    public  static String getMonthFromdate(String date){
        //TODO
        return "JAN";
    }


    public  static String getTraceId(Object reqId){
        return null!=reqId?(String)reqId:UUID.randomUUID().toString();
    }

    public static String getIpFromRequest(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
