package com.example.thesis;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.adapter.RecyclerviewAdapter;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.NotesData;
import com.example.thesis.model.StepsData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Button btn_add,  btn_end;

    CheckBox sun, mon, tue, wed, thu, fri, sat, notes;
    String appearanceCheck = "", resultAppearance;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    TextInputEditText nameti, descti, finishti;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    StepsData step;
    FloatingActionButton del_btn;
    AutoCompleteTextView color_ti, count_ti;
    final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    final int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    List<AutoCompleteTextView> list = new ArrayList<AutoCompleteTextView>();
    List<Integer>listid = new ArrayList<Integer>();
    @SuppressLint("SetTextI18n")

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String[] colors = getResources().getStringArray(R.array.colors);
        ArrayAdapter arrayAdapter = new ArrayAdapter(EditActivity.this,R.layout.dropdown_item, colors);
        color_ti.setAdapter(arrayAdapter);

        String[] count = getResources().getStringArray(R.array.count);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(EditActivity.this,R.layout.dropdown_item2, count);
        count_ti.setAdapter(arrayAdapter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_edit);
       DataBaseHelper db = new DataBaseHelper(EditActivity.this);
        final LinearLayout lm = (LinearLayout) findViewById(R.id.linear_pickers2);
        step = db.findOne(getIntent().getIntExtra("step_id",1));
        btn_add = findViewById(R.id.act_sub);
        btn_end = findViewById(R.id.button_add);
        mon = findViewById(R.id.mon_act);
        tue = findViewById(R.id.tue_act);
        wed = findViewById(R.id.wed_act);
        thu= findViewById(R.id.thu_act);
        fri= findViewById(R.id.fri_act);
        sat = findViewById(R.id.sat_act);
        sun = findViewById(R.id.sun_act);
        notes = findViewById(R.id.notes_on_check);
        nameti = findViewById(R.id.name_ti);
        descti = findViewById(R.id.desc_ti);
        finishti = findViewById(R.id.finish_ti);
        del_btn = findViewById(R.id.delete_btn);
        color_ti = findViewById(R.id.color_ti);
        count_ti = findViewById(R.id.count_ti);


        String newmonth = "" + month;
        String newday = "" + dayOfMonth;
        if (month < 10) newmonth = "0" + month;
        if (dayOfMonth < 10) newday = "0" + dayOfMonth;

        color_ti.setText(step.getColor());
        count_ti.setText(String.valueOf(step.getStepTimes()));
        nameti.setText(step.getStepName());
        descti.setText(step.getDescription());
        finishti.setText(step.getStepFinish());
        if (step.getStepAppearance().contains("1")) sun.setChecked(true);
        if (step.getStepAppearance().contains("2")) mon.setChecked(true);
        if (step.getStepAppearance().contains("3")) tue.setChecked(true);
        if (step.getStepAppearance().contains("4")) wed.setChecked(true);
        if (step.getStepAppearance().contains("5")) thu.setChecked(true);
        if (step.getStepAppearance().contains("6")) fri.setChecked(true);
        if (step.getStepAppearance().contains("7")) sat.setChecked(true);



        if (db.noteExist(step.getStepId())){
            notes.setChecked(true);
            timeTabs(lm, step.getStepTimes());
            List<NotesData> notecount = db.findOneNote(step.getStepId());
            for(int i = 0; i < step.getStepTimes() ; i++){
                int textid = list.get(i).getId();
                int resID = getResources().getIdentifier(String.valueOf(textid), "id", getPackageName());
                AutoCompleteTextView text =  findViewById(resID);
                text.setText(notecount.get(i).getTime());
            }
        }

        count_ti.addTextChangedListener(new TextWatcher() {
            int count = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                count = list.size();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lm.removeAllViews();
                listid.clear();
                list.clear();
                DataBaseHelper db = new DataBaseHelper(EditActivity.this);
                List<NotesData> notecount = db.findOneNote(step.getStepId());
                timeTabs(lm, Integer.parseInt(count_ti.getText().toString()));

                for(int i = 0; i < notecount.size() ; i++) {
                    Integer textid = list.get(i).getId();
                    int resID = getResources().getIdentifier(String.valueOf(textid), "id", getPackageName());
                    AutoCompleteTextView text = findViewById(i);
                    text.setText(notecount.get(i).getTime());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });

        notes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    lm.removeAllViews();
                    listid.clear();
                    list.clear();
                    timeTabs(lm,Integer.parseInt(count_ti.getText().toString()));
                }
                else {
                    lm.removeAllViews();
                    listid.clear();
                    list.clear();

                }
            }
        });


        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(EditActivity.this);
                SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                int points = Integer.parseInt(sharedPreferences.getString("points", "0"));
                sharedEditor.putString("points", String.valueOf(points-5));
                sharedEditor.commit();

                DataBaseHelper db = new DataBaseHelper(EditActivity.this);
                StepsData stepsData = new StepsData(step.getStepId(), nameti.getText().toString(),descti.getText().toString(),color_ti.getText().toString(),step.getStepStart(),finishti.getText().toString(),resultAppearance, Integer.parseInt(count_ti.getText().toString()));
                boolean b = db.deleteOne(stepsData);
                NotificationManager notificationManager = (NotificationManager)getSystemService(EditActivity.NOTIFICATION_SERVICE);
                notificationManager.cancel(step.getStepId());
                if (b)
                    Toast.makeText(EditActivity.this, "true", Toast.LENGTH_LONG).show();
                else  Toast.makeText(EditActivity.this, "false", Toast.LENGTH_LONG).show();

                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                StepsData stepsData;
                appearanceCheck = "";
                if (sun.isChecked()) appearanceCheck+="1";
                if (mon.isChecked()) appearanceCheck+="2";
                if (tue.isChecked()) appearanceCheck+="3";
                if (wed.isChecked()) appearanceCheck+="4";
                if (thu.isChecked()) appearanceCheck+="5";
                if (fri.isChecked()) appearanceCheck+="6";
                if (sat.isChecked()) appearanceCheck+="7";
                resultAppearance = appearanceCheck;
                List<NotesData> notesData = new ArrayList<>();
                SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm");
                for (int i = 0; i < list.size(); i++){
                    NotesData note = new NotesData(step.getStepId(), list.get(i).getText().toString());
                    notesData.add(note);
                    NotificationManager notificationManager = (NotificationManager)getSystemService(EditActivity.NOTIFICATION_SERVICE);
                    notificationManager.cancel(step.getStepId());


                    try {
                        Date date = timeformat.parse(list.get(i).getText().toString());
                    }
                    catch (ParseException e){

                    }
                    long alarmStartTime = date.getTime();

                    Intent intent = new Intent(EditActivity.this, ReminderBroadcast.class);
                    intent.putExtra("step_id", step.getStepId());
                    intent.putExtra("message", nameti.getText().toString());
                    // PendingIntent
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            EditActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                    );

                    // AlarmManager
                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);


                }

                try {
                    stepsData = new StepsData(step.getStepId(), nameti.getText().toString(),descti.getText().toString(),color_ti.getText().toString(), step.getStepStart(),finishti.getText().toString(),resultAppearance, Integer.parseInt(count_ti.getText().toString()));
                    Toast.makeText(EditActivity.this, resultAppearance, Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    stepsData = new StepsData(-1, "error","","","","","", 0);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(EditActivity.this);
                // EDIT
                boolean b = dataBaseHelper.updateOne(stepsData);
                dataBaseHelper.updateOneNotes(step.getStepId(),notesData);
               // if (b)
                   // Toast.makeText(EditActivity.this, "true", Toast.LENGTH_LONG).show();
               // else  Toast.makeText(EditActivity.this, "false", Toast.LENGTH_LONG).show();


                SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(EditActivity.this);
                SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                String edittask = sharedPreferences.getString("edittask", "0");

                if (edittask.equals("0")) {
                        dataBaseHelper.finishAchievement(10);
                        sharedEditor.putString("edittask", "1");
                        Toast.makeText(EditActivity.this, "Achievement 10", Toast.LENGTH_LONG).show();
                    }
                sharedEditor.commit();

                finish();


            }
        });



    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, this, Calendar.getInstance().get(Calendar.YEAR),
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int textid = listid.get(0);
        int resID = getResources().getIdentifier(String.valueOf(textid), "id", getPackageName());
        AutoCompleteTextView text =  findViewById(resID);
        text.setText(hourOfDay +":"+ minute);
        listid.remove(0);

    }

    public void showTimePickerDialog(View v) {
        TimePickerDialog newFragment = new TimePickerDialog(EditActivity.this,this,Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE), true);
        newFragment.show();
    }
    public void timeTabs(LinearLayout lm, int n){
        for (int i = 0; i < n; i++){
            TextInputLayout l = new TextInputLayout(lm.getContext());
            l.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
            l.setEndIconActivated(true);
            AutoCompleteTextView t = new AutoCompleteTextView(l.getContext());
            t.setInputType(0);
            t.setHeight(150);
            t.setWidth(50);
            t.setTextAlignment(AutoCompleteTextView.TEXT_ALIGNMENT_CENTER);
            t.setId(i);
            l.addView(t);
            list.add(t);
            lm.addView(l);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int textid = v.getId();
                    listid.add(textid);
                    showTimePickerDialog(v);
                }
            });

        }
            DataBaseHelper db = new DataBaseHelper(EditActivity.this);

    }
}