package com.vcu.teamnapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        getMessage(context, intent);
    }
    public String getMessage(Context context, Intent intent) {
        String messageBody = "";
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                if(smsMessage.getOriginatingAddress().equals("795-16"))
                    messageBody += smsMessage.getMessageBody();
                else
                    break;
            }
        }
        return messageBody;
        }
}

