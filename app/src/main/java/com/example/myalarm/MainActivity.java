package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    LinearLayout linearLayout;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hiding action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // hiding status bar
        //WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        // setting color to status bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.BLACK);



        databaseHelper = new DatabaseHelper(getApplicationContext());
        linearLayout = findViewById(R.id.linearLayoutContainer);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        // invoking for creating card view
        displayData();

        // adding event listener to floating action button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // adding new data to database
                databaseHelper.addData("alarm",12,30,0);
                // populate data from database
                displayData();
            }
        });


    }

    // populating data
    void displayData(){

        // clearing all card view
        linearLayout.removeAllViews();

        // reading all data from database
        Cursor cursor = databaseHelper.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }else{

            // iterate over all the data
            while (cursor.moveToNext()){

                // initializing data from database cursor
                String rowId = cursor.getString(0);
                String title = cursor.getString(1);
                int hour = cursor.getInt(2);
                int minute = cursor.getInt(3);
                int activeStatus = cursor.getInt(4);

                // inflate new card view
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.card,null);

                // initializing some views within card view
                TextView time = view1.findViewById(R.id.textViewTime);
                Switch toggle = view1.findViewById(R.id.switch1);
                ImageView deleteView = view1.findViewById(R.id.imageViewDelete);

                // populate data to each view
                time.setText(String.format("%02d",cursor.getInt(2)) +" : "+ String.format("%02d",cursor.getInt(3)));
                toggle.setChecked(cursor.getInt(4)==1 ?true :false);

                // adding event listener to toggle switch
                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        // setting or cancelling alarm
                        if(isChecked){

                            Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                            alarmIntent.putExtra(AlarmClock.EXTRA_HOUR,hour);
                            alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
                            alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE,"alarm from MyAlarm app");
                            alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);

                            try{
                                startActivity(alarmIntent);
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(), "no alarm app found !", Toast.LENGTH_SHORT).show();
                            }

                        }else{

                            Intent alarmIntent = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
                            alarmIntent.putExtra(AlarmClock.ALARM_SEARCH_MODE_LABEL,"alarm from MyAlarm app");

                            try{
                                startActivity(alarmIntent);
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(), "no alarm app found !", Toast.LENGTH_SHORT).show();
                            }

                        }

                        // updating to database
                        databaseHelper.updateData(rowId,title,hour,minute,isChecked?1:0);
                        displayData();
                    }
                });

                // adding event listener to delete icon
                deleteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseHelper.deleteData(rowId);
                        displayData();
                    }
                });

                // initializing some data for TimePickerDialog
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinutes = calendar.get(Calendar.MINUTE);

                // creating new timePickerDialog
                TimePickerDialog timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour1, int minute1) {
                        databaseHelper.updateData(rowId,title,hour1,minute1,(toggle.isChecked())?1:0);
                        displayData();
                    }
                },currentHour,currentMinutes,false);

                // adding event listener to time TextView
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timePicker.show();
                    }
                });

                // adding card view to parent linearLayout
                linearLayout.addView(view1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}









