package com.example.thesis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class StatisticsFragment extends Fragment {
    TextView stat_percent, textdays6, textdays5, textdays4, textdays3, textdays2,textday1, texttoday, vasya;
    ProgressBar progress_bar, progress_barhor, prog_bar1, prog_bar2, prog_bar3, prog_bar4, prog_bar5, prog_bar6;
    AutoCompleteTextView stat_ti;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    ImageView helper_image;
    DataBaseHelper db;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container,false);
        final Context thiscontext = container.getContext();
         db = new DataBaseHelper(thiscontext);



        helper_image = view.findViewById(R.id.stat_vasya_img);

        stat_percent = view.findViewById(R.id.stat_percent);
        progress_bar = view.findViewById(R.id.progress_bar);
        progress_barhor = view.findViewById(R.id.progressBar);
        progress_barhor.setMax(100);
        prog_bar1 = view.findViewById(R.id.progressBar1);
        prog_bar2 = view.findViewById(R.id.progressBar2);
        prog_bar3 = view.findViewById(R.id.progressBar3);
        prog_bar4 = view.findViewById(R.id.progressBar4);
        prog_bar5 = view.findViewById(R.id.progressBar5);
        prog_bar6 = view.findViewById(R.id.progressBar6);
        texttoday = view.findViewById(R.id.texttoday);
        textday1 = view.findViewById(R.id.textday1);
        textdays2 = view.findViewById(R.id.textdays2);
        textdays3 = view.findViewById(R.id.textdays3);
        textdays4 = view.findViewById(R.id.textdays4);
        textdays5 = view.findViewById(R.id.textdays5);
        textdays6 = view.findViewById(R.id.textdays6);
        vasya = view.findViewById(R.id.vasya_stat);
        stat_ti = view.findViewById(R.id.stats_ti);
        stat_ti.setText("Все");

        List<String> colors = new ArrayList<>();
        colors.add(0,"Все");
        final List<StepsData> stepsData = db.getEveryone();
        for (int i = 1; i < stepsData.size(); i++)
            colors.add(stepsData.get(i).getStepName());
        ArrayAdapter arrayAdapter = new ArrayAdapter(thiscontext,R.layout.dropdown_item, colors);
        stat_ti.setAdapter(arrayAdapter);
        getStat(thiscontext, 0);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(thiscontext);
        sharedEditor = sharedPreferences.edit();
        int daily = Integer.parseInt(sharedPreferences.getString("dailystreak", "0"));
        int points = Integer.parseInt(sharedPreferences.getString("points", "0"));
        int helper = Integer.parseInt(sharedPreferences.getString("helper", "0"));
        if (helper == 0)
            helper_image.setBackgroundResource(R.drawable.vas_hap_hand);
        else if (helper == 1)
            helper_image.setBackgroundResource(R.drawable.luda_demo);
       int result = db.getStatisticsMinus(0,0);
        String datestreak = sharedPreferences.getString("datestreak", "0");




        stat_ti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                getStat(thiscontext,0);
                else getStat(thiscontext,stepsData.get(position).getStepId());
                if (sharedPreferences.getString("viewstat","0").equals("0")){
                    db.finishAchievement(3);
                    sharedEditor.putString("viewstat", "1");
                    sharedEditor.commit();
                    Toast.makeText(thiscontext, "Achievement3", Toast.LENGTH_LONG).show();
                }
            }
        });



        return view;

    }

    public String getDayofWeek(int day){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch(dayOfWeek){
            case(1):
                return "Вc";
            case(2):
                return "Пн";
            case(3):
                return "Вт";
            case(4):
                return "Ср";
            case(5):
                return "Чт";
            case(6):
                return "Пт";
            case(7):
                return "Сб";
            default:
                return "er";
        }
    }

public void getStat(Context context, int id){
        DataBaseHelper db = new DataBaseHelper(context);
    int result = db.getStatisticsMinus(0,id);
    int result1 = db.getStatisticsMinus(-1,id);
    int result2 = db.getStatisticsMinus(-2,id);
    int result3 = db.getStatisticsMinus(-3,id);
    int result4 = db.getStatisticsMinus(-4,id);
    int result5 = db.getStatisticsMinus(-5,id);
    int result6 = db.getStatisticsMinus(-6,id);




    stat_percent.setText(String.valueOf(result) + "%");
    progress_bar.setProgress(result);
    progress_barhor.setProgress(result);
    prog_bar1.setProgress(result1);
    prog_bar2.setProgress(result2);
    prog_bar3.setProgress(result3);
    prog_bar4.setProgress(result4);
    prog_bar5.setProgress(result5);
    prog_bar6.setProgress(result6);
    texttoday.setText(getDayofWeek(0));
    textday1.setText(getDayofWeek(-1));
    textdays2.setText(getDayofWeek(-2));
    textdays3.setText(getDayofWeek(-3));
    textdays4.setText(getDayofWeek(-4));
    textdays5.setText(getDayofWeek(-5));
    textdays6.setText(getDayofWeek(-6));

    if (result == 100)
        vasya.setText("Уф, на сегодня всё. Какие мы молодцы!");
    else if ( result > 80)
        vasya.setText("Давай, осталось немного! Раз плюнуть.");
    else if (result > 50 )
        vasya.setText("Полпути позади. Так держать!");
    else if (result > 30)
        vasya.setText("Давай в том же темпе, дружище");
    else  vasya.setText("Впереди еще много работы, но мы справимся.");
}


}
