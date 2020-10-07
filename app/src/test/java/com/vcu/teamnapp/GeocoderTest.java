package com.vcu.teamnapp;

import com.vcu.geocoder.Geocoder;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GeocoderTest {

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void geocode() throws IOException, JSONException, ParseException {
        Geocoder geocoder = new Geocoder();
        String[] arr = geocoder.geocode("VCU ALERT Robbery CORE MP Campus --Broad/Harrison. Police on scene. Avoid area.");
        String latitude = arr[0];
        String longitude = arr[1];
        assertEquals(latitude, "37.5514764");
        assertEquals(longitude, "-77.45195129999999");
    }
    @Test
    public void geocode1() throws IOException, JSONException, ParseException {
        Geocoder geocoder = new Geocoder();
        String[] arr = geocoder.geocode("VCU ALERT\n" +
                "Shooting OFF MCV Campus (Conclusion) --4th/Grace. Situation resolved. Go to alert.vcu.edu for additional information");
        String latitude = arr[0];
        String longitude = arr[1];
        assertEquals(latitude, "37.5400695");
        assertEquals(longitude, "-77.4299163");
    }

}