package com.trackandtrail.util;



import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.trackandtrail.common.AuditableBase;

import java.util.Date;

public class CommonMapper {

//    public static <T> T map(Object object, Class<T> clazz) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();
//            //Gson gson = new GsonBuilder().create();
//            String json = mapper.writeValueAsString(object);
//            return gson.fromJson(json, clazz);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public static <E> void setAuditable(E object, String user) {
//        AuditableBase auditable = (AuditableBase) object;
//        auditable.setCreatedDate(new Date());
//        auditable.setCreatedBy(user);
//        auditable.setLastModifiedDate(new Date());
//        auditable.setLastModifiedBy(user);
//    }
//
//    public static <E> void setUpdateAuditable(AuditableBase auditable,  String user) {
//    	  auditable.setLastModifiedDate(new Date());
//          auditable.setLastModifiedBy(user);
//    }
}

