package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
    ArrayList<String> titles;
    ArrayList<Integer> hours,minutes;
    ArrayList<Boolean> activeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hiding action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // hiding status bar
        //WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.BLACK);


        titles = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        activeStatus = new ArrayList<>();

        databaseHelper = new DatabaseHelper(getApplicationContext());
        linearLayout = findViewById(R.id.linearLayoutContainer);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        displayData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseHelper.addData("alarm",12,30,1);
                displayData();
            }
        });


    }

    void displayData(){

        linearLayout.removeAllViews();
        Cursor cursor = databaseHelper.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                titles.add(cursor.getString(1));
                hours.add(cursor.getInt(2));
                minutes.add(cursor.getInt(3));
                activeStatus.add(cursor.getInt(4)==1 ?true :false);


                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.card,null);

                TextView time = view1.findViewById(R.id.textViewTime);
                Switch toggle = view1.findViewById(R.id.switch1);

                time.setText(cursor.getInt(2) +" : "+cursor.getInt(3));
                toggle.setChecked(cursor.getInt(4)==1 ?true :false);


                linearLayout.addView(view1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}









