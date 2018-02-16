package sarah.nci.ie.reminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class AddActivity extends AppCompatActivity {

    //Subject picker
    String json;
    Button bSubject;

    //Date picker - Reference https://www.youtube.com/watch?v=hwe1abDO2Ag
    private static final String TAG = "AddActivity";
    private Button mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;

    //Save function
    Button saveButton;
    private EditText title;
    private String datee;
    private String subject;

    //Notification
    private int year, month, day;
    private Calendar cal;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Date picker - Reference https://www.youtube.com/watch?v=hwe1abDO2Ag
        mDisplayDate = (Button) findViewById(R.id.btnDueDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a calendar object
                cal = Calendar.getInstance();

                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG, "OnDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                //Create a String date, and set textView to this date.
                date = day + "/ " + month + " / " + year;
                mDisplayDate.setText(date);
            }
        };

        //Subject picker - Open DialogList
        bSubject = (Button) findViewById(R.id.btnSubject);
        bSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, SubjectDialogActivity.class));
            }
        });

        //The save function
        title = ((EditText)findViewById(R.id.eTitle));
        datee = date;

        saveButton = findViewById(R.id.button2);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                //Pass the data into list ca
                CA ca = new CA(subject, title.getText().toString(), date);

                //Insert into database
                db.caDao().insertAll(ca);

                //Get the time from the date picker
                long selectedTimestamp = cal.getTimeInMillis();
                long currentTime = Calendar.getInstance().getTimeInMillis();
                long notificationTime = currentTime - selectedTimestamp;

                //Notification - Reference https://www.youtube.com/watch?v=k-tREnlQsrk
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent = new Intent("singh.ajit.action.DISPLAY_NOTIFICATION");
                PendingIntent broadcast = PendingIntent.getBroadcast(AddActivity.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

                //Back to Main activity
                startActivity(new Intent(AddActivity.this, MainActivity.class));
            }
        });



    }

    //Refresh button - update the subject button's text
    public void refresh(View view){
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(AddActivity.this);
        json = mPrefs.getString("MyObject", "");
        if (json != null) {
            Toast.makeText(getBaseContext(), json, Toast.LENGTH_LONG).show();
            //Update the button's text
            bSubject.setText(json);
            //Pass the text to the CA list
            subject = json;
        }
    }




}
