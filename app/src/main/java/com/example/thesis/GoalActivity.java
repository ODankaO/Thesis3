package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class GoalActivity extends AppCompatActivity {
    Button goalbtn;
    TextInputLayout goallayout;
    TextInputEditText goaltext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        goalbtn = findViewById(R.id.goalbutton);
        goallayout = findViewById(R.id.GoalLayout);
        goaltext = findViewById(R.id.GoalEdittext);

        goalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GoalActivity.this);
                sharedEditor = sharedPreferences.edit();
                sharedEditor.putString("firststart", "1");
                sharedEditor.putString("maingoal", goaltext.getText().toString().trim());
                sharedEditor.apply();
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}