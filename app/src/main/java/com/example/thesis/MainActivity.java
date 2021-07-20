package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.thesis.model.DataBaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigation;
    NestedScrollView nested_content;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;

    boolean isNavigationHide = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sharedEditor = sharedPreferences.edit();

       DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

            navigation = findViewById(R.id.navigation);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navigation, navController);
    }
}




