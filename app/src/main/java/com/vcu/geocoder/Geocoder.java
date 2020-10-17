package com.vcu.geocoder;

import android.os.AsyncTask;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Geocoder extends AsyncTask<String, Void, String[]> {

    private static final String API_KEY = "AIzaSyAl-RZHT5WG66Ghz_wcL79U185LaSFJM74";
    @Override
    public String[] doInBackground(String... strings) {
        Geocoder geocoder = new Geocoder();
        String[] coordinates = new String[2];
        try {
            coordinates = geocoder.geocode(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (ParseException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
    //Returns the coordinates in a String array
    public String[] geocode(String vcuAlert) throws IOException, ParseException {
        //if(vcuAlert.contains("Conclusion")){
            //call method to remove "Pin" on the map
        //}
        vcuAlert = formatMessage(vcuAlert);

        final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json?address=" + vcuAlert;

        String urlString = GEOCODING_RESOURCE + "&key=" + API_KEY;
        URL url = new URL(urlString);

        JSONObject json = getJsonResponse(url, "GET");
        String[] coordinates = new String[2];
        coordinates[0] = json.get("lat").toString();
        coordinates[1] = json.get("lng").toString();
        return coordinates;
    }
    //returns the entire API response as a JSON object
    public JSONObject getJsonResponse(URL url, String requestMethod) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        String response = "";
        Scanner in = new Scanner(url.openStream());
        while (in.hasNext()) {
            response += in.nextLine();
        }
        in.close();

        JSONObject json = parseLocation(response);

        return json;
    }
    //Returns the coordinates from the API response
    public JSONObject parseLocation (String input) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(input);
        JSONArray results = (JSONArray) json.get("results");
        JSONObject location = (JSONObject) results.get(0);
        JSONObject geometry = (JSONObject) location.get("geometry");

        JSONObject coordinates = (JSONObject) geometry.get("location");

        return coordinates;
    }
    //Removes all spaces and replaces them with + to make it api readable
    //Adds richmond to the end of the string to provide more accuracy
    public String formatMessage(String s){
        s = s.replaceAll(" ", "+");
        s = s.substring(10);
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '-' && s.charAt(i+1) == '-'){
                s = s.substring(i+2);
                break;
            }
        }
        s += "+Richmond";
        return s;
    }
}
