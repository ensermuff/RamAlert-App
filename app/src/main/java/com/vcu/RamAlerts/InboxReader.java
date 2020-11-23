package com.vcu.RamAlerts;

import android.content.ContentResolver;
import android.content.Context;

import android.database.Cursor;
import android.provider.Telephony;
import android.widget.Toast;


public class InboxReader {

    public String messageBody;
    public void sendVcuAlert(Context context) {
        messageBody = "";
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
            if (c != null) {
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    if (number.equals("795-16") || number.equals("79516")) {
                        messageBody = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                        break;
                    } else
                        continue;
                }
            }

            if (!messageBody.equals("")) {
            DisplayAlertFragment displayAlertFragment = DisplayAlertFragment.Instance;
//            if(messageBody.contains("Conclusion")){
//                displayAlert.removeMarkerFromList();
//            }
            displayAlertFragment.setVcuAlert(messageBody);
            displayAlertFragment.placeAlert();
            Toast.makeText(context, messageBody, Toast.LENGTH_SHORT).show();
            }
        }
    }
