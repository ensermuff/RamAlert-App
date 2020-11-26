package com.vcu.RamAlerts;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel1";
    private NotificationManagerCompat notificationManagerCompat;
    Switch enableNotification;
    double userLatitude;
    double userLongitude;
    double alertLatitude;
    double alertLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        createNotificationChannel();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notifications();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only if the user is on android
        // Oreo or higher (API 26+) because the NotificationChannel class
        // is new and not available on low API levels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1 that determines whether the distance between the two locations is at most 1 mile");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }

    public void notifications (){
        enableNotification = findViewById(R.id.notificationSwitch);
        enableNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enableNotification.isChecked()) {
                    //When the switch is checked
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    enableNotification.setChecked(true);

                    LocationSettings locationSettingsInstance = new LocationSettings();
                    userLatitude = locationSettingsInstance.getLatitude();
                    userLongitude = locationSettingsInstance.getLongitude();

                    DisplayAlertFragment displayAlertFragmentInstance = new DisplayAlertFragment();
                    alertLatitude = displayAlertFragmentInstance.getAlertLatitude();
                    alertLongitude = displayAlertFragmentInstance.getAlertLongitude();
                    // if distance is less than or equal to 1 mile
                    if (distance(userLatitude, userLongitude, alertLatitude, alertLongitude) <= 1) {
                        String message = "Distance of alert to user location is within a mile.";
                        notificationBuilder(message);
                    } else {
                        String message2 = "Distance of alert to user location isn't within a mile.";
                        notificationBuilder(message2);
                    }
                }else {
                    //When the switch isn't checked
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    enableNotification.setChecked(false);
                    String message3 = "Notification switch isn't turned on";
                    notificationBuilder(message3);
                }
            }
        });
    }

    private double distance (double userLatitude, double userLongitude, double alertLatitude, double alertLongitude){
        double earthRadius = 3958.75;

        double dLatitude = Math.toRadians(alertLatitude - userLatitude);
        double dLongitude = Math.toRadians(alertLongitude - userLongitude);

        double sindLatitude = Math.sin(dLatitude/2);
        double sindLongitude = Math.sin(dLongitude/2);

        double a = Math.pow(sindLatitude, 2) + Math.pow(sindLongitude, 2)
                * Math.cos(Math.toRadians(userLatitude) * Math.cos(Math.toRadians(alertLatitude)));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; //output distance in miles

    }
    public void notificationBuilder(String message){
        Notification notificationMyBuilder = new NotificationCompat.Builder(Notifications.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("New Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
//                .setAutoCancel(true);
        notificationManagerCompat.notify(1, notificationMyBuilder);
    }
 }
