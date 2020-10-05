package com.vcu.teamnapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SMSListenerTest {
    @Mock
    Context MockContext;



    @Test
    public void onReceive() {

    }

    @Test
    public void getMessage() {

        Intent MockIntent = mock(Intent.class);
        SMSListener myClass = new SMSListener();
        SmsMessage message = mock(SmsMessage.class);
        SmsMessage[] messageArr = new SmsMessage[3];
        messageArr[0] = message;

        when(MockIntent.getAction()).thenReturn(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        when(Telephony.Sms.Intents.getMessagesFromIntent(MockIntent)).thenReturn(messageArr);
        when(message.getOriginatingAddress()).thenReturn("795-16");
        when(message.getMessageBody()).thenReturn("This is a test text message");

        assertEquals("This is a test text mssage", myClass.getMessage(MockContext, MockIntent));
    }
    public void mockMessage(){

    }
}
