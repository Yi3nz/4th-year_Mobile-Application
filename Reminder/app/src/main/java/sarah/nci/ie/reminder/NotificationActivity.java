package sarah.nci.ie.reminder;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Notification on click activity
 * Reference https://github.com/ajitsing/AlarmManagerAndReceiver/tree/master/app/src/main/java/bootcamp/android/demoapp
 */

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Once click into the notification, bring the user back to the main page of the app.
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
