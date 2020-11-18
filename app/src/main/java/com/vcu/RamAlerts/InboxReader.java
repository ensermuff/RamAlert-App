package com.vcu.RamAlerts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.Telephony;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class InboxReader {
    public void sendVcuAlert(Context context) {
        String messageBody = "";
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
//            DisplayAlert displayAlert = new DisplayAlert();
////            if(messageBody.contains("Conclusion")){
////                displayAlert.removeMarkerFromList();
////            }
//            displayAlert.setVcuAlert(messageBody);
//            displayAlert.placeAlert();
                Toast.makeText(context, messageBody, Toast.LENGTH_SHORT).show();
            }
        }
    }


}