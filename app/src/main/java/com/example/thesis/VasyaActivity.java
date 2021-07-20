package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thesis.model.DataBaseHelper;

public class VasyaActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    ImageView vasya;
    TextView phrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vasya);
        /*dataBaseHelper = new DataBaseHelper(this);
        if (!dataBaseHelper.VasyaisEmpty()) {
            dataBaseHelper.fillVasyaAll(1);
        }
        vasya = this.findViewById(R.id.vasya);
        phrase = this.findViewById(R.id.vasya_phrase);
        phrase.setText(dataBaseHelper.randomPhrase(1));
        vasya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            phrase.setText(dataBaseHelper.randomPhrase(1));
            }
        });

         */

    }
}