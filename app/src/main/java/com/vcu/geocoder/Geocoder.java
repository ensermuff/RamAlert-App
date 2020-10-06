package com.vcu.geocoder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Geocoder {
    private static final String API_KEY = "AIzaSyAl-RZHT5WG66Ghz_wcL79U185LaSFJM74";
    public String geocode(String message) throws IOException, InterruptedException{
        final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json?address=" + message;
        HttpURLConnection connection = null;
        URL url = new URL(GEOCODING_RESOURCE + "&key=" + API_KEY);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if(connection.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED){
            
        }
        return message;
    }

}
