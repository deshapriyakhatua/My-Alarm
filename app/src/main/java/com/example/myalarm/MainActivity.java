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
import android.widget.CompoundButton;
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
                String rowId = cursor.getString(0);
                String title = cursor.getString(1);
                int hour = cursor.getInt(2);
                int minute = cursor.getInt(3);
                int activeStatus = cursor.getInt(4);


                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.card,null);

                TextView time = view1.findViewById(R.id.textViewTime);
                Switch toggle = view1.findViewById(R.id.switch1);

                time.setText(cursor.getInt(2) +" : "+cursor.getInt(3));
                toggle.setChecked(cursor.getInt(4)==1 ?true :false);

                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        databaseHelper.updateData(rowId,title,hour,minute,isChecked?1:0);
                        displayData();
                    }
                });


                linearLayout.addView(view1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}









