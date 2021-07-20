package com.example.thesis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.thesis.adapter.RecyclerviewAdapter;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.NotesData;
import com.example.thesis.model.StepsData;
import com.example.thesis.model.StepsStatisticsData;
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

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Button btn_add,  btn_end;

    CheckBox sun, mon, tue, wed, thu, fri, sat, notes, finish_check;
    String appearanceCheck = "", resultAppearance;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    TextInputEditText nameti, descti, startti, finishti;
    TextInputLayout finish_til;
    AutoCompleteTextView color_ti, count_ti;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    RecyclerviewAdapter recycler;
    List<AutoCompleteTextView> list = new ArrayList<>();
    List<Integer>listid = new ArrayList<>();
    private int notificationId = 1;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        String[] colors = getResources().getStringArray(R.array.colors);
        ArrayAdapter arrayAdapter = new ArrayAdapter(FormActivity.this,R.layout.dropdown_item, colors);
        color_ti.setAdapter(arrayAdapter);

        String[] count = getResources().getStringArray(R.array.count);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(FormActivity.this,R.layout.dropdown_item2, count);
        count_ti.setAdapter(arrayAdapter2);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_form);
       createNotificationChannel();



        final LinearLayout lm = (LinearLayout) findViewById(R.id.linear_pickers);
        final int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int year = Calendar.getInstance().get(Calendar.YEAR);
       final int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
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
        startti = findViewById(R.id.start_ti);
        color_ti = findViewById(R.id.color_ti);
        count_ti = findViewById(R.id.count_ti);
        color_ti.setText("Red", false);
        count_ti.setText("1", false);
        finish_check = findViewById(R.id.finish_check);
        finish_til = findViewById(R.id.finish_til);


        String newmonth = "" + month;
        String newday = "" + dayOfMonth;
        if (month < 10) newmonth = "0" + month;
        if (dayOfMonth < 10) newday = "0" + dayOfMonth;

        startti.setText("" + year + "-" + newmonth +"-" + newday);
        finishti = findViewById(R.id.finish_ti);

   count_ti.addTextChangedListener(new TextWatcher() {
       int count = 0;
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        count = list.size();
       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           notes.setChecked(false);
       }

       @Override
       public void afterTextChanged(Editable s) {



       }
   });
        notes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    timeTabs(lm);
                }
                else {
                    lm.removeAllViews();
                    list.clear();

                }
            }
        });

        finish_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    finish_til.setEnabled(false);
                    finishti.setText("#");
                }
                    else{
                    finish_til.setEnabled(true);
                        finishti.setText("");
                }
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });


        btn_add.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v){
                if (!nameti.getText().toString().trim().isEmpty() && !descti.getText().toString().trim().isEmpty() && !startti.getText().toString().trim().isEmpty() && !finishti.getText().toString().trim().isEmpty()){
                    List<NotesData> notesData = new ArrayList<>();
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(FormActivity.this);






                    StepsData stepsData;
                    if (sun.isChecked()) appearanceCheck += "1";
                    if (mon.isChecked()) appearanceCheck += "2";
                    if (tue.isChecked()) appearanceCheck += "3";
                    if (wed.isChecked()) appearanceCheck += "4";
                    if (thu.isChecked()) appearanceCheck += "5";
                    if (fri.isChecked()) appearanceCheck += "6";
                    if (sat.isChecked()) appearanceCheck += "7";
                    resultAppearance = appearanceCheck;



                    for (int i = 0; i < list.size(); i++) {
                        NotesData note = new NotesData(dataBaseHelper.getMaxId() + 1, list.get(i).getText().toString());
                        notesData.add(note);


                           String[] hour1 =  list.get(i).getText().toString().split(":");
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt( hour1[0]));
                            cal.set(Calendar.MINUTE, Integer.parseInt( hour1[1]));
                            cal.set(Calendar.SECOND, 0);

                            Intent intent = new Intent(FormActivity.this, ReminderBroadcast.class);
                            intent.putExtra("step_id", dataBaseHelper.getMaxId() + 1);
                            intent.putExtra("message", nameti.getText().toString());
                            // PendingIntent
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    FormActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                            );

                            // AlarmManager
                            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


                    }

                    try {
                        stepsData = new StepsData(-1, nameti.getText().toString(), descti.getText().toString(), color_ti.getText().toString(), startti.getText().toString(), finishti.getText().toString(), resultAppearance, Integer.parseInt(count_ti.getText().toString()));
                      //  Toast.makeText(FormActivity.this, resultAppearance, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        stepsData = new StepsData(-1, "error", "", "", "", "", "", 0);

                    }


                    boolean b = dataBaseHelper.addOne(stepsData);
                    dataBaseHelper.addOneNotes(notesData);


                    SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(FormActivity.this);
                    SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                    int points = Integer.parseInt(sharedPreferences.getString("points", "0"));
                    sharedEditor.putString("points", String.valueOf(points+5));
                    String onetask = sharedPreferences.getString("onetask", "0");
                    String fivetasks = sharedPreferences.getString("fivetasks", "0");
                    if (onetask.equals("0")) {
                        dataBaseHelper.finishAchievement(1);  sharedEditor.putString("onetask", "1");
                        Toast.makeText(FormActivity.this, "Achievement 1 unlocked", Toast.LENGTH_LONG).show();
                    }
                    if (fivetasks.equals("0")) {
                        if (dataBaseHelper.getEveryone().size() >= 5) {
                            dataBaseHelper.finishAchievement(8);
                            sharedEditor.putString("fivetasks", "1");
                            Toast.makeText(FormActivity.this, "Achievement 8", Toast.LENGTH_LONG).show();
                        }
                    }
                    sharedEditor.commit();
                    setResult(RESULT_OK);
                    finish();
                }
                else
                Toast.makeText(FormActivity.this, "Fill all the fields", Toast.LENGTH_LONG);


                

            }
        });

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this, this, Calendar.getInstance().get(Calendar.YEAR),
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
        text.setText(hourOfDay+":"+minute);
        listid.remove(0);

    }

    public void showTimePickerDialog(View v) {
        TimePickerDialog newFragment = new TimePickerDialog(FormActivity.this,this,Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE), true);
        newFragment.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void timeTabs(LinearLayout lm){
        for (int i = 1; i <= Integer.parseInt(count_ti.getText().toString()); i++){
            LinearLayout ll = new LinearLayout(FormActivity.this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
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
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ExampleReminderChannel";
            String description = "Channel for example reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyExample", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}