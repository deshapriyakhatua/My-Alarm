package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hiding action bar
        getSupportActionBar().hide();

        // hiding status bar
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.BLACK);

        hour = findViewById(R.id.editTextHour);
        minute = findViewById(R.id.editTextMinute);

        hour.addTextChangedListener(new TextWatcher() {
            String beforeChange;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeChange = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2 > i1){
                    int hours = Integer.parseInt(charSequence.toString());

                    if(hours>23 || charSequence.length()>2){
                        hour.setText(beforeChange);
                        hour.setSelection(beforeChange.length()-1);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        minute.addTextChangedListener(new TextWatcher() {
            String beforeChange;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeChange = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(i2 > i1){
                    int minutes = Integer.parseInt(charSequence.toString());

                    if(minutes>59 || charSequence.length()>2){
                        minute.setText(beforeChange);
                        minute.setSelection(beforeChange.length()-1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}