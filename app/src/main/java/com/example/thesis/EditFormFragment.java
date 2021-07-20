package com.example.thesis;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class EditFormFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
Context thiscontext;
    Button btn_add,  btn_end;
    ImageButton imageButton;
    CheckBox sun, mon, tue, wed, thu, fri, sat;
    String appearanceCheck = "", resultAppearance;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    TextInputEditText nameti, descti, startti, finishti;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    StepsData step;
    final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    final int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editform, container, false);
        thiscontext = container.getContext();

      /*  DataBaseHelper db = new DataBaseHelper(thiscontext);
        step = db.findOne(getArguments().getInt("step_id"));

        btn_add = (Button) view.findViewById(R.id.act_sub);
        btn_end = view.findViewById(R.id.button_add);
        mon = (CheckBox) view.findViewById(R.id.mon_act);
        tue = (CheckBox) view.findViewById(R.id.tue_act);
        wed = (CheckBox) view.findViewById(R.id.wed_act);
        thu= (CheckBox) view.findViewById(R.id.thu_act);
        fri= (CheckBox) view.findViewById(R.id.fri_act);
        sat = (CheckBox) view.findViewById(R.id.sat_act);
        sun = (CheckBox) view.findViewById(R.id.sun_act);
        nameti = view.findViewById(R.id.name_ti);
        descti = view.findViewById(R.id.desc_ti);
        startti = view.findViewById(R.id.start_ti);


        String newmonth = "" + month;
        String newday = "" + dayOfMonth;
        if (month < 10) newmonth = "0" + month;
        if (dayOfMonth < 10) newday = "0" + dayOfMonth;

        nameti.setText(step.getStepName());
        descti.setText(step.getDescription());
        startti.setText(step.getStepStart());
        finishti = view.findViewById(R.id.finish_ti);
        finishti.setText(step.getStepFinish());
        if (step.getStepAppearance().contains("1")) sun.setChecked(true);
        if (step.getStepAppearance().contains("2")) mon.setChecked(true);
        if (step.getStepAppearance().contains("3")) tue.setChecked(true);
        if (step.getStepAppearance().contains("4")) wed.setChecked(true);
        if (step.getStepAppearance().contains("5")) thu.setChecked(true);
        if (step.getStepAppearance().contains("6")) fri.setChecked(true);
        if (step.getStepAppearance().contains("7")) sat.setChecked(true);


        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });


        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                StepsData stepsData;
                if (sun.isChecked()) appearanceCheck+="1";
                if (mon.isChecked()) appearanceCheck+="2";
                if (tue.isChecked()) appearanceCheck+="3";
                if (wed.isChecked()) appearanceCheck+="4";
                if (thu.isChecked()) appearanceCheck+="5";
                if (fri.isChecked()) appearanceCheck+="6";
                if (sat.isChecked()) appearanceCheck+="7";
                resultAppearance = appearanceCheck;

                try {
                    stepsData = new StepsData(step.getStepId(), nameti.getText().toString(),descti.getText().toString(),startti.getText().toString(),finishti.getText().toString(),resultAppearance);
                    Toast.makeText(thiscontext, resultAppearance, Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    stepsData = new StepsData(-1, "error","","","","");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(thiscontext);
                // EDIT
                boolean b = dataBaseHelper.updateOne(stepsData);
                if (b)
                Toast.makeText(thiscontext, "true", Toast.LENGTH_LONG).show();
                else  Toast.makeText(thiscontext, "false", Toast.LENGTH_LONG).show();



            }


        });*/
        return view;

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(thiscontext, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH)+1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String newmonth = "" + month+1;
        String newday = "" + dayOfMonth;
        if (month < 10) newmonth = "0" + month;
        if (dayOfMonth < 10) newday = "0" + dayOfMonth;

        String date = "" + year + "-" + (newmonth) +"-" + newday;
        finishti.setText(date);
    }
}