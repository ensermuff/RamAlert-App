package com.vcu.geocoder;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import org.json.JSONException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Geocoder {
    private static final String API_KEY = "AIzaSyAl-RZHT5WG66Ghz_wcL79U185LaSFJM74";
    public String[] geocode(String vcuAlert) throws IOException, JSONException, ParseException {
        vcuAlert = vcuAlert.replaceAll(" ", "+") + "Richmond";

        final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json?address=" + vcuAlert;

        String urlString = GEOCODING_RESOURCE + "&key=" + API_KEY;
        URL url = new URL(urlString);

        JSONObject json = getJsonResponse(url, "GET");
        String[] coordinates = new String[2];
        coordinates[0] = json.get("lat").toString();
        coordinates[1] = json.get("lng").toString();
        return coordinates;
    }
    public JSONObject getJsonResponse(URL url, String requestMethod) throws IOException, JSONException, ParseException {
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
        JSONObject json = parseLocation(response);


        return json;
    }
    public JSONObject parseLocation (String input) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(input);
        JSONArray results = (JSONArray) json.get("results");
        JSONObject location = (JSONObject) results.get(0);
        JSONObject geometry = (JSONObject) location.get("geometry");

        JSONObject coordinates = (JSONObject) geometry.get("location");

        return coordinates;
    }

}
