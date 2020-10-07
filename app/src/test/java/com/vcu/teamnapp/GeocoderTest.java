package com.vcu.teamnapp;

import com.vcu.geocoder.Geocoder;

import org.json.JSONException;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GeocoderTest {

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void geocode() throws IOException, InterruptedException, JSONException {
        Geocoder geocoder = new Geocoder();
        System.out.print(geocoder.geocode("VCU ALERT Robbery CORE MP Campus-- Broad/Harrison. Police on scene. Avoid area."));
    }
}