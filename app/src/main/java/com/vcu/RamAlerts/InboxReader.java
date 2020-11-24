package com.vcu.RamAlerts;

import android.content.ContentResolver;
import android.content.Context;

import android.database.Cursor;
import android.provider.Telephony;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InboxReader {

    public String messageBody;
    public void sendVcuAlert(Context context) {
        messageBody = "";
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        ArrayList<String> messageArr = new ArrayList<>();
            if (c != null) {
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    if (number.equals("795-16") || number.equals("79516")) {
                        messageBody = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                        if(occuredWithinLast24Hours(c.getLong(c.getColumnIndex(Telephony.Sms.DATE)))){
                            messageArr.add(messageBody);
                        }
                    }
                }
            }

            if (!messageBody.equals("")) {
            DisplayAlertFragment displayAlertFragment = DisplayAlertFragment.Instance;
//            if(messageBody.contains("Conclusion")){
//                displayAlert.removeMarkerFromList();
//
                for(String message : messageArr) {
                    displayAlertFragment.setVcuAlert(message);
                    displayAlertFragment.placeAlert();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

            }
        }
    public boolean occuredWithinLast24Hours(long date){
        Date currentDate = new Date();
        int currentHour = Integer.parseInt((String) android.text.format.DateFormat.format("hh", currentDate));
        int messageHour = Integer.parseInt((String) android.text.format.DateFormat.format("hh", date));
        int currentDay = Integer.parseInt((String) android.text.format.DateFormat.format("dd", currentDate));
        int messageDay = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date));
        return Math.abs(currentHour - messageHour) < 12 && currentDay - messageDay <= 1;
    }
    }
