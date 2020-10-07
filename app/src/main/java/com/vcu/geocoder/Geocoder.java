package com.vcu.geocoder;

import java.io.IOException;
import org.json.JSONException;

import org.json.JSONObject;
import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Geocoder {
    private static final String API_KEY = "AIzaSyAl-RZHT5WG66Ghz_wcL79U185LaSFJM74";
    public String geocode(String vcuAlert) throws IOException, InterruptedException, JSONException {
        vcuAlert = vcuAlert.replaceAll(" ", "+") + "Richmond";
        final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json?address=" + vcuAlert;

        String urlString = GEOCODING_RESOURCE + "&key=" + API_KEY;
        URL url = new URL(urlString);

        System.out.println("This is the response" + getJsonResponse(url, "GET").toString());


        return vcuAlert;
    }
    public JSONObject getJsonResponse(URL url, String requestMethod) throws IOException, JSONException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        String response = "";
        if(connection.getResponseCode() == 200) {
            Scanner in = new Scanner(url.openStream());
            while (in.hasNext()) {
                response += in.nextLine();
            }
            in.close();
        }
        System.out.println(response);
        JSONObject json = parseLocation(response);
        return json;
    }
    public JSONObject parseLocation (String input) throws JSONException {
        JSONObject json = new JSONObject(input);
        System.out.println(json);
        JSONArray results = json.getJSONArray("results");
        for(int i = 0; i < results.length(); i++){
            System.out.println(results.getJSONObject(i).toString());
        }
        return json;
    }

}
