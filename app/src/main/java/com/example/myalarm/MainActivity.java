package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        hour = findViewById(R.id.editTextHour);
        minute = findViewById(R.id.editTextMinute);

        hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2 > i1){
                    int hours = Integer.valueOf(charSequence.toString());

                    if(hours>23){ hour.setText("23"); }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        minute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(i2 > i1){
                    int minutes = Integer.valueOf(charSequence.toString());

                    if(minutes>59){ minute.setText("59"); }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}