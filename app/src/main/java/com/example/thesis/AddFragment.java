package com.example.thesis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.adapter.RecyclerviewAdapter;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;
import com.example.thesis.model.StepsStatisticsData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddFragment extends Fragment implements RecyclerViewClickInterface {

    RecyclerView stepsRecycler;
    RecyclerviewAdapter recyclerviewAdapter;
    List<StepsData> stepsDataList;
    List<StepsStatisticsData> stepsStatsDataList;
    DataBaseHelper dataBaseHelper;
    Context thiscontext;
    FloatingActionButton btn_form;
    Button ach_btn;
    TextView level_text, goal_text;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewadd = inflater.inflate(R.layout.fragment_add, container,false);


       thiscontext = container.getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(thiscontext);
        sharedEditor = sharedPreferences.edit();
        if(sharedPreferences.getString("firststart","0").equals("0")) {

            sharedEditor.putString("firststart", "1");
            sharedEditor.apply();
           startActivityForResult(new Intent(thiscontext, GoalActivity.class), 10001);


        }



        dataBaseHelper = new DataBaseHelper(thiscontext);
        stepsDataList = dataBaseHelper.getToday();
        stepsStatsDataList = dataBaseHelper.getStatistics();



        int result = dataBaseHelper.getStatisticsMinus(0,0);
        if (result == 100) {
            int daily = Integer.parseInt(sharedPreferences.getString("dailystreak", "0"));
            String datestreak = sharedPreferences.getString("datestreak", "0");
            Toast.makeText(thiscontext, "100 for today", Toast.LENGTH_LONG).show();
            if (!datestreak.equals(String.valueOf(Calendar.getInstance().get(Calendar.DATE)))) {
                sharedEditor.putString("dailystreak", String.valueOf(daily + 1));
                sharedEditor.putString("datestreak", String.valueOf(Calendar.getInstance().get(Calendar.DATE)));
                sharedEditor.commit();

            }

            if ( sharedPreferences.getString("dailystreak", "0").equals("3"))
            { dataBaseHelper.finishAchievement(6); sharedEditor.putString("threestat", "1");
                Toast.makeText(thiscontext, "Achievement 6", Toast.LENGTH_LONG).show();
            }


            if ( sharedPreferences.getString("dailystreak", "0").equals("10")) { dataBaseHelper.finishAchievement(7); sharedEditor.putString("tenstat", "1");
                Toast.makeText(thiscontext, "Achievement 7", Toast.LENGTH_LONG).show();
            }
        }




        if (stepsDataList.size()> 0 && dataBaseHelper.getToday().size() >  dataBaseHelper.getStatisticsToday().size()) {
                dataBaseHelper.fillStatistics(stepsDataList); }

        if (dataBaseHelper.achievementsisEmpty()) {
            dataBaseHelper.fillAchievementsAll();
        }


        if(!sharedPreferences.contains("level")) {

            sharedEditor.putString("level", "1");

        }

        if(!sharedPreferences.contains("helper")) {

            sharedEditor.putString("helper", "0");

        }

        if(!sharedPreferences.contains("points")) {

            sharedEditor.putString("points", "0");

        }

        if(!sharedPreferences.contains("dailystreak")) {

            sharedEditor.putString("dailystreak", "0");

        }

        if(!sharedPreferences.contains("datestreak")) {

            sharedEditor.putString("datestreak", "0");
        }

        if(!sharedPreferences.contains("onetask")) {

            sharedEditor.putString("onetask", "0");
        }

        if(!sharedPreferences.contains("unfinishtask")) {

            sharedEditor.putString("unfinishtask", "0");
        }

        if(!sharedPreferences.contains("edittask")) {

            sharedEditor.putString("edittask", "0");
        }

        if(!sharedPreferences.contains("fivetasks")) {

            sharedEditor.putString("fivetasks", "0");
        }

        if(!sharedPreferences.contains("firstfinished")) {

            sharedEditor.putString("firstfinished", "0");
        }

        if(!sharedPreferences.contains("noskipfinish")) {

            sharedEditor.putString("noskipfinish", "0");
        }

        if(!sharedPreferences.contains("mintendayfinish")) {

            sharedEditor.putString("mintendayfinish", "0");
        }

        if(!sharedPreferences.contains("viewstat")) {

            sharedEditor.putString("viewstat", "0");
        }

        if(!sharedPreferences.contains("threestat")) {

            sharedEditor.putString("threestat", "0");
        }
        if(!sharedPreferences.contains("tenstat")) {

            sharedEditor.putString("tenstat", "0");
        }
        if(!sharedPreferences.contains("counttalk")) {

            sharedEditor.putString("counttalk", "0");
        }



        int points = Integer.parseInt(sharedPreferences.getString("points","0"));
        if ( points >= 150)
            sharedEditor.putString("level", "3");
        else if (points >= 100)
            sharedEditor.putString("level", "2");

        sharedEditor.commit();



        stepsRecycler = (RecyclerView) viewadd.findViewById(R.id.stepsRecycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thiscontext, RecyclerView.VERTICAL, false);
        stepsRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapter = new RecyclerviewAdapter(thiscontext, stepsDataList, stepsStatsDataList,this);
        stepsRecycler.setAdapter(recyclerviewAdapter);
        btn_form = viewadd.findViewById(R.id.btn_form);
        level_text = viewadd.findViewById(R.id.level_text);
        goal_text = viewadd.findViewById(R.id.goal_text);
        goal_text.setText(sharedPreferences.getString("maingoal","0"));




        btn_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(thiscontext, FormActivity.class), 10001);
            }
        });



        if(sharedPreferences.contains("level")) {
            level_text.setText(sharedPreferences.getString("points", "0"));
        }


        return viewadd;
    }


    @Override
    public void onItemClick(int position) {
        StepsStatisticsData stepsDataList2 = dataBaseHelper.getStatisticsToday2(stepsDataList.get(position));
        if (stepsDataList2.getStepStatus() != 1) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(thiscontext);
            sharedEditor = sharedPreferences.edit();
            int points = Integer.parseInt(sharedPreferences.getString("points", "0"));
            sharedEditor = sharedPreferences.edit();
            sharedEditor.putString("points", String.valueOf(points + 1));
            if (sharedPreferences.getString("firstfinished","0").equals("0")) {dataBaseHelper.finishAchievement(2);  sharedEditor.putString("firstfinished", "1");
                Toast.makeText(thiscontext, "Achievement2", Toast.LENGTH_LONG).show();

            }
            sharedEditor.commit();
        }


        if (stepsDataList.size() > dataBaseHelper.getStatisticsToday().size()) {
            if (!dataBaseHelper.DoesExist(stepsDataList.get(0)))
                dataBaseHelper.fillStatistics(stepsDataList); }


            dataBaseHelper.makeFinished(stepsDataList.get(position));

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onItemLongClick(int position) {
        StepsStatisticsData stepsDataList2 = dataBaseHelper.getStatisticsToday2(stepsDataList.get(position));
        if (stepsDataList2.getStepCount() > 0) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(thiscontext);
            sharedEditor = sharedPreferences.edit();
            int points = Integer.parseInt(sharedPreferences.getString("points", "0"));
            sharedEditor = sharedPreferences.edit();
            sharedEditor.putString("points", String.valueOf(points - 1));
            if (sharedPreferences.getString("unfinishtask","0").equals("0")) {dataBaseHelper.finishAchievement(9);  sharedEditor.putString("unfinishtask", "1");
                Toast.makeText(thiscontext, "Achievement 9", Toast.LENGTH_LONG).show();

            }
            sharedEditor.commit();
        }


        Toast.makeText(thiscontext, "unfinished", Toast.LENGTH_LONG).show();
            dataBaseHelper.makeUnfinished(stepsDataList.get(position));

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK))
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
        ft.detach(this).attach(this).commit();
    }

}

