package com.vcu.geocoder;


import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class Geocoder {
    private static final String API_KEY = "AIzaSyAl-RZHT5WG66Ghz_wcL79U185LaSFJM74";
    public String geocode(String vcuAlert) throws IOException, InterruptedException{
        vcuAlert = vcuAlert.replaceAll(" ", "+") + "Richmond";
        final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json?address=" + vcuAlert;

        String url = GEOCODING_RESOURCE + "&key=" + API_KEY;
        System.out.println("This is the response" + getJsonResponse(url).toString());


        return vcuAlert;
    }
    public JsonObjectRequest getJsonResponse(String url){

        return json;
    }

}
