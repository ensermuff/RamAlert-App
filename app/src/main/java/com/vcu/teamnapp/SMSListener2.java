package com.vcu.teamnapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSListener2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Message received!", Toast.LENGTH_SHORT).show();
        sendVcuAlert(context, intent);
    }
    public void sendVcuAlert(Context context, Intent intent) {
        String messageBody = "";
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                if(smsMessage.getOriginatingAddress().equals("795-16") || smsMessage.getOriginatingAddress().equals("79516"))
                    messageBody += smsMessage.getMessageBody();
                else
                    break;
            }
        }
        if(!messageBody.equals("")) {
            DisplayAlert displayAlert = new DisplayAlert();
//            if(messageBody.contains("Conclusion")){
//                displayAlert.removeMarkerFromList();
//            }
            displayAlert.setVcuAlert(messageBody);
            displayAlert.placeAlert();
            Toast.makeText(context, messageBody, Toast.LENGTH_SHORT).show();

        }
    }
}
