package com.example.thesis.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 16;
    public static final String STEPS_TABLE = "STEPS";
    public static final String COLUMN_STEP_ID = "STEP_ID";
    public static final String COLUMN_STEP_NAME = "STEP_NAME";
    public static final String COLUMN_STEP_DESCRIPTION = "STEP_DESCRIPTION";
    public static final String COLUMN_STEP_COLOR = "STEP_COLOR";

    public static final String STEPS_STATISTICS_TABLE = "STEPS_STATISTICS";
    public static final String COLUMN_STEP_STATISTICS_ID = "STEP_ID";
    public static final String COLUMN_STEP_STATISTICS_ACTION_DATE = "ACTION_DATE";
    public static final String COLUMN_STEP_STATISTICS_STATUS = "STATUS";
    public static final String COLUMN_STEP_STATISTICS_COUNT = "COUNT";


    public static final String VASYA_TABLE = "VASYA";
    public static final String COLUMN_VASYA_PHRASE_ID = "PHRASE_ID";
    public static final String COLUMN_VASYA_LEVEL = "LEVEL";
    public static final String COLUMN_VASYA_PHRASE = "PHRASE";
    public static final String COLUMN_VASYA_EMOTION = "EMOTION";

    public static final String TIMECONF_TABLE = "TIMECONF";
    public static final String COLUMN_TIMECONF_ID = "STEP_ID";
    public static final String COLUMN_TIMECONF_START = "STEP_START";
    public static final String COLUMN_TIMECONF_FINISH = "STEP_FINISH";
    public static final String COLUMN_TIMECONF_APPEARANCE = "STEP_APPEARANCE";
    public static final String COLUMN_TIMECONF_TIMES = "TIMES";

    public static final String NOTIFICATIONS_TABLE = "NOTIFICATIONS";
    public static final String COLUMN_NOTIFICATIONS_ID = "STEP_ID";
    public static final String COLUMN_NOTIFICATIONS_TIME = "NOTIFICATION_TIME";


    public static final String ACHIEVEMENTS_TABLE = "ACHIEVEMENTS";
    public static final String COLUMN_ACHIEVEMENT_ID = "ACHIEVEMENT_ID";
    public static final String COLUMN_ACHIEVEMENT_TITLE = "ACHIEVEMENT_TITLE";
    public static final String COLUMN_ACHIEVEMENT_TEXT = "ACHIEVEMENT_TEXT";
    public static final String COLUMN_ACHIEVEMENT_STATUS = "ACHIEVEMENT_STATUS";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);


    public DataBaseHelper(@Nullable Context context) {
        super(context, "tasks_db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STEPS_TABLE +
                " (" + COLUMN_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STEP_NAME + " TEXT, " + COLUMN_STEP_DESCRIPTION +
                " TEXT," +COLUMN_STEP_COLOR+ " TEXT)";
        String createTableStatement2 = "CREATE TABLE " + STEPS_STATISTICS_TABLE +
                " (" + COLUMN_STEP_ID + " INTEGER , " + COLUMN_STEP_STATISTICS_ACTION_DATE + " DATE, " + COLUMN_STEP_STATISTICS_STATUS +
                " INTEGER, "+COLUMN_STEP_STATISTICS_COUNT+" INTEGER, PRIMARY KEY(" + COLUMN_STEP_STATISTICS_ID + ", " + COLUMN_STEP_STATISTICS_ACTION_DATE + "))";
        String createTableStatement3 = "CREATE TABLE " + VASYA_TABLE +
                " (" + COLUMN_VASYA_PHRASE_ID + " INTEGER PRIMARY KEY, " + COLUMN_VASYA_LEVEL + " INTEGER, " + COLUMN_VASYA_PHRASE +
                " TEXT, " + COLUMN_VASYA_EMOTION + " TEXT)";
        String createTableStatement4 = "CREATE TABLE " + TIMECONF_TABLE +
                " (" + COLUMN_TIMECONF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TIMECONF_START + " DATE, " + COLUMN_TIMECONF_FINISH +
                " DATE, " +COLUMN_TIMECONF_APPEARANCE+ " TEXT," +COLUMN_TIMECONF_TIMES+ " INTEGER)";
        String createTableStatement5 = "CREATE TABLE " + NOTIFICATIONS_TABLE +
                " (" + COLUMN_NOTIFICATIONS_ID + " INTEGER, " + COLUMN_NOTIFICATIONS_TIME + " TEXT)";
        String createTableStatement6 = "CREATE TABLE " + ACHIEVEMENTS_TABLE +
                " (" + COLUMN_ACHIEVEMENT_ID + " INTEGER PRIMARY KEY, " + COLUMN_ACHIEVEMENT_TITLE + " TEXT, " + COLUMN_ACHIEVEMENT_TEXT +
                " TEXT, " +COLUMN_ACHIEVEMENT_STATUS+ " INTEGER)";

        db.execSQL(createTableStatement);
        db.execSQL(createTableStatement2);
        db.execSQL(createTableStatement3);
        db.execSQL(createTableStatement4);
        db.execSQL(createTableStatement5);
        db.execSQL(createTableStatement6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + STEPS_TABLE);
       db.execSQL("DROP TABLE IF EXISTS " + STEPS_STATISTICS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + VASYA_TABLE);
       db.execSQL("DROP TABLE IF EXISTS " + TIMECONF_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACHIEVEMENTS_TABLE);

       onCreate(db);


    }

    public StepsData findOne(int id){
        String query = "SELECT "+STEPS_TABLE+".* , "+TIMECONF_TABLE+".* FROM " + STEPS_TABLE +
                " inner join "+TIMECONF_TABLE+" on "+TIMECONF_TABLE+".STEP_ID = "+STEPS_TABLE+
                ".STEP_ID WHERE "+STEPS_TABLE+".STEP_ID = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        StepsData newStep = new StepsData(-1,"error","","error", "", "","", 0);
        if (cursor.moveToFirst()) {
            do {
                int stepId = cursor.getInt(0);
                String stepName = cursor.getString(1);
                String stepDesc = cursor.getString(2);
                String stepColor = cursor.getString(3);
                String stepStart = cursor.getString(5);
                String stepFinish = cursor.getString(6);
                String stepAppear = cursor.getString(7);
                int stepTimes = cursor.getInt(8);
                newStep = new StepsData(stepId, stepName, stepDesc, stepColor, stepStart, stepFinish, stepAppear, stepTimes);
            }
            while (cursor.moveToNext());
        } else {
            //failure
        }
        cursor.close();
        db.close();
        return newStep;
    }

    public List<StepsData> getToday() {
        List<StepsData> returnList = new ArrayList<>();

        String query = "SELECT "+STEPS_TABLE+".*, "+TIMECONF_TABLE+".* FROM " + STEPS_TABLE + " inner join "+TIMECONF_TABLE+" on "+TIMECONF_TABLE+".STEP_ID = "+STEPS_TABLE+
                ".STEP_ID WHERE " + COLUMN_TIMECONF_APPEARANCE + " LIKE '%" + dayOfWeek + "%' AND (" + COLUMN_TIMECONF_FINISH + " >= date('now') OR "+COLUMN_TIMECONF_FINISH+ " LIKE  '#' ) AND " + COLUMN_TIMECONF_START + " <= date('now')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int stepId = cursor.getInt(0);
                String stepName = cursor.getString(1);
                String stepDesc = cursor.getString(2);
                String stepColor = cursor.getString(3);
                String stepStart = cursor.getString(5);
                String stepFinish = cursor.getString(6);
                String stepAppear = cursor.getString(7);
                int stepTimes = cursor.getInt(8);
                StepsData newStep = new StepsData(stepId, stepName, stepDesc, stepColor, stepStart, stepFinish, stepAppear, stepTimes);
                returnList.add(newStep);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean addOne(StepsData stepsData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvstep = new ContentValues();
        ContentValues cvtime = new ContentValues();
        cvstep.put(COLUMN_STEP_NAME, stepsData.getStepName());
        cvstep.put(COLUMN_STEP_DESCRIPTION, stepsData.getDescription());
        cvstep.put(COLUMN_STEP_COLOR, stepsData.getColor());
        cvtime.put(COLUMN_TIMECONF_START, stepsData.getStepStart());
        cvtime.put(COLUMN_TIMECONF_FINISH, stepsData.getStepFinish());
        cvtime.put(COLUMN_TIMECONF_APPEARANCE, stepsData.getStepAppearance());
        cvtime.put(COLUMN_TIMECONF_TIMES, stepsData.getStepTimes());
        long insert = db.insert(STEPS_TABLE, null, cvstep);
        db.insert(TIMECONF_TABLE, null, cvtime);
        return insert != -1;

    }

    public boolean updateOne (StepsData stepsData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvstep = new ContentValues();
        ContentValues cvtime = new ContentValues();
        cvstep.put(COLUMN_STEP_NAME, stepsData.getStepName());
        cvstep.put(COLUMN_STEP_DESCRIPTION, stepsData.getDescription());
        cvstep.put(COLUMN_STEP_COLOR, stepsData.getDescription());
        cvtime.put(COLUMN_TIMECONF_START, stepsData.getStepStart());
        cvtime.put(COLUMN_TIMECONF_FINISH, stepsData.getStepFinish());
        cvtime.put(COLUMN_TIMECONF_APPEARANCE, stepsData.getStepAppearance());
        cvtime.put(COLUMN_TIMECONF_TIMES, stepsData.getStepTimes());
        long update = db.update(STEPS_TABLE, cvstep, COLUMN_STEP_ID + " = ?", new String[]{Integer.toString(stepsData.getStepId())});
        db.update(TIMECONF_TABLE, cvtime, COLUMN_TIMECONF_ID + " = ?", new String[]{Integer.toString(stepsData.getStepId())});
        return update != -1;

    }
    public List<StepsData> getEveryone() {
        List<StepsData> returnList = new ArrayList<>();

        String query = "SELECT "+STEPS_TABLE+".* , "+TIMECONF_TABLE+".* FROM " + STEPS_TABLE +
                " inner join "+TIMECONF_TABLE+" on "+TIMECONF_TABLE+".STEP_ID = "+STEPS_TABLE+
                ".STEP_ID";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int stepId = cursor.getInt(0);
                String stepName = cursor.getString(1);
                String stepDesc = cursor.getString(2);
                String stepColor = cursor.getString(3);
                String stepStart = cursor.getString(5);
                String stepFinish = cursor.getString(6);
                String stepAppear = cursor.getString(7);
                int stepTimes = cursor.getInt(8);
                StepsData newStep = new StepsData(stepId, stepName, stepDesc, stepColor, stepStart, stepFinish, stepAppear, stepTimes);
                returnList.add(newStep);
            }
            while (cursor.moveToNext());
        } else {
            //failure
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteOne(StepsData stepsData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + STEPS_TABLE + " WHERE " + COLUMN_STEP_ID + " = " + stepsData.getStepId();
        String queryString2 = "DELETE FROM " + STEPS_STATISTICS_TABLE + " WHERE " + COLUMN_STEP_ID + " = " + stepsData.getStepId() + " AND " + COLUMN_STEP_STATISTICS_ACTION_DATE+" >= date('now')";
        String queryString3 = "DELETE FROM " + TIMECONF_TABLE + " WHERE " + COLUMN_TIMECONF_ID + " = " + stepsData.getStepId();
        String queryString4 = "DELETE FROM " + NOTIFICATIONS_TABLE + " WHERE " + COLUMN_NOTIFICATIONS_ID + " = " + stepsData.getStepId();
        db.execSQL(queryString);
        db.execSQL(queryString2);
        db.execSQL(queryString3);
        db.execSQL(queryString4);
        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }

    public void makeUnfinished(StepsData stepsData) {

        SQLiteDatabase db = this.getWritableDatabase();
        StepsStatisticsData st = getStatisticsToday2(stepsData);
        String queryString;
        if (st.getStepCount() > 0) {
            queryString = " UPDATE " + STEPS_STATISTICS_TABLE + " SET " + COLUMN_STEP_STATISTICS_STATUS + " = 0, " + COLUMN_STEP_STATISTICS_COUNT + " = " + COLUMN_STEP_STATISTICS_COUNT + " - 1 WHERE  " + COLUMN_STEP_STATISTICS_ID + " = " + stepsData.getStepId() + " AND " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = '" + dateFormat.format(date) + "'";
            db.execSQL(queryString);
        }
    }

    public void makeFinished(StepsData stepsData) {

        SQLiteDatabase db = this.getWritableDatabase();
        StepsStatisticsData st = getStatisticsToday2(stepsData);
        String queryString = "";
        if (st.getStepStatus() != 1)
            if (stepsData.getStepTimes()==1|| ((stepsData.getStepTimes() - st.getStepCount()) == 1)){
                queryString = " UPDATE " + STEPS_STATISTICS_TABLE + " SET " + COLUMN_STEP_STATISTICS_STATUS + " = 1, "+COLUMN_STEP_STATISTICS_COUNT+" = "+COLUMN_STEP_STATISTICS_COUNT+" + 1 WHERE " + COLUMN_STEP_STATISTICS_ID + " = " + stepsData.getStepId() + " AND " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = '" + dateFormat.format(date) + "'";
                db.execSQL(queryString);}
            else if ( st.getStepCount() < stepsData.getStepTimes()) {queryString = " UPDATE " + STEPS_STATISTICS_TABLE + " SET "+COLUMN_STEP_STATISTICS_COUNT+" = "+COLUMN_STEP_STATISTICS_COUNT+" + 1  WHERE " + COLUMN_STEP_STATISTICS_ID + " = " + stepsData.getStepId() + " AND " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = '" + dateFormat.format(date) + "'";
                db.execSQL(queryString);}


    }

public int getMaxId () {
    String query = "SELECT MAX(STEP_ID) FROM " + STEPS_TABLE;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    int stepId = 0;
    if (cursor.moveToFirst()) {
        do {
            stepId = cursor.getInt(0);
        } while (cursor.moveToNext());
    }
    return stepId;
}
    public List<NotesData> findOneNote (int id){
        String query = "SELECT * FROM " + NOTIFICATIONS_TABLE + " WHERE "+ COLUMN_NOTIFICATIONS_ID + " = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int stepId = 0;
        String noteTime = "";
        List<NotesData> notelist = new ArrayList<NotesData>();
        NotesData note = new NotesData(-1,"");
        if (cursor.moveToFirst()) {
            do {
                stepId = cursor.getInt(0);
                noteTime = cursor.getString(1);
                note = new NotesData(stepId, noteTime);
                notelist.add(note);
            } while (cursor.moveToNext());

        }
        return notelist;
}

    public boolean noteExist (int id){
        String query = "SELECT * FROM " + NOTIFICATIONS_TABLE + " WHERE "+ COLUMN_NOTIFICATIONS_ID + " = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            } else {
                cursor.close();
                return true;
            }
        } catch (Exception ex) {
            cursor.close();
            return false;
        }

    }

    public void addOneNotes(List<NotesData> returnList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < returnList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NOTIFICATIONS_ID, returnList.get(i).getStepId());
            cv.put(COLUMN_NOTIFICATIONS_TIME, returnList.get(i).getTime());
            long insert = db.insert(NOTIFICATIONS_TABLE, null, cv);
        }
    }



    public void updateOneNotes(int id, List<NotesData> returnList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString;
        queryString = "DELETE FROM " + NOTIFICATIONS_TABLE + " WHERE " + COLUMN_NOTIFICATIONS_ID + " = " + id;
        db.execSQL(queryString);
        if (returnList.size() > 0)
        for (int i = 0; i < returnList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NOTIFICATIONS_ID, returnList.get(i).getStepId());
            cv.put(COLUMN_NOTIFICATIONS_TIME, returnList.get(i).getTime());
            long insert = db.insert(NOTIFICATIONS_TABLE, null, cv);
        }
    }




    public static String getCalculatedDate(int days) {
        String dateFormat = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public int getStatisticsMinus ( int day, int id){
        String minus_date = getCalculatedDate(day);
        String query;
        if (id == 0)
        query = "SELECT "+ COLUMN_STEP_STATISTICS_STATUS +" FROM " + STEPS_STATISTICS_TABLE + " WHERE " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = '" + minus_date + "'";
        else query = "SELECT "+ COLUMN_STEP_STATISTICS_STATUS +" FROM " + STEPS_STATISTICS_TABLE + " WHERE " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = '" + minus_date + "' AND "+ COLUMN_STEP_STATISTICS_ID + " = "+ id;
        ArrayList<Integer> list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int status = cursor.getInt(0);
                list.add(status);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        int count1 = 0;
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) == 1) count1++;
        }
        if (count1 == 0) return 0;
        else
        return count1*100/list.size();
    }


    public void fillStatistics(List<StepsData> returnList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < returnList.size(); i++) {
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_STEP_STATISTICS_ID, returnList.get(i).getStepId());
            cv.put(COLUMN_STEP_STATISTICS_ACTION_DATE, dateFormat.format(date));
            cv.put(COLUMN_STEP_STATISTICS_STATUS, 0);
            cv.put(COLUMN_STEP_STATISTICS_COUNT,0);
            long insert = db.insert(STEPS_STATISTICS_TABLE, null, cv);
        }
    }


    public List<StepsStatisticsData> getStatistics() {
        List<StepsStatisticsData> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + STEPS_STATISTICS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int stepId = cursor.getInt(0);
                String stepDate = cursor.getString(1);
                int status = cursor.getInt(2);
                int count = cursor.getInt(3);

                StepsStatisticsData newStep = new StepsStatisticsData(stepId, stepDate, status, count);
                returnList.add(newStep);
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return returnList;
    }

    public List<StepsStatisticsData> getStatisticsToday() {
        List<StepsStatisticsData> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + STEPS_STATISTICS_TABLE + " WHERE " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = date('now')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int stepId = cursor.getInt(0);
                String stepDate = cursor.getString(1);
                int status = cursor.getInt(2);
                int count = cursor.getInt(3);
                StepsStatisticsData newStep = new StepsStatisticsData(stepId, stepDate, status, count);
                returnList.add(newStep);
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return returnList;
    }

    public StepsStatisticsData getStatisticsToday2(StepsData stepsData) {

        String query = "SELECT * FROM " + STEPS_STATISTICS_TABLE + " WHERE " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = date('now') AND STEP_ID = "+stepsData.getStepId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        StepsStatisticsData newStep = new StepsStatisticsData(-1,"",0,0);
        if (cursor.moveToFirst()) {
            do {
                int stepId = cursor.getInt(0);
                String stepDate = cursor.getString(1);
                int status = cursor.getInt(2);
                int count = cursor.getInt(3);
                newStep = new StepsStatisticsData(stepId, stepDate, status, count);

            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return newStep;
    }


    public boolean fillVasya(int id, int level, String phrase, String emotion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_VASYA_PHRASE_ID, id);
        cv.put(COLUMN_VASYA_LEVEL, level);
        cv.put(COLUMN_VASYA_PHRASE, phrase);
        cv.put(COLUMN_VASYA_EMOTION, emotion);
        long insert = db.insert(VASYA_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public void emptyVasya(int level){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE  FROM "+ VASYA_TABLE + " WHERE " + COLUMN_VASYA_LEVEL + " = " + level;
        db.execSQL(query);
    }

    public void fillVasyaAll(int level) {
        if (level == 3) {
            fillVasya(1, 1, "Привет, друг! Продуктивный день сегодня?", "vas_hap_hand");
            fillVasya(2, 1, "Я бы почитал книжек, да братва не поймет.", "vas_unsure_hand");
            fillVasya(3, 1, "А в школе я ведь хорошистом был", "vas_unsure_think");
            fillVasya(4, 1, "Скучно сидеть просто так, полистать бы что-нибудь.", "vas_hap_hand");
            fillVasya(5, 1, "Вспомнил! Геометрия!", "vas_hap_geometry");
        }
        else if (level == 2) {
            fillVasya(21, 2, "А Шерлок Холмс тот еще плут!", "vas_hap_book");
            fillVasya(22, 2, "Слушай, а ЕГЭ можно в любом возрасте сдать?", "vas_unsure_think_book");
            fillVasya(23, 2, "Матушка моя говорит, я обленился совсем. Может, работу поискать?", "vas_unsure_think_book");
        }
        else if (level == 1){
            fillVasya(31, 1, "А в интернете и правда можно что-угодно найти?", "vas_bench_quest");
            fillVasya(32, 1, "Ха-ха, никогда бы не подумал, что мне понравится учиться", "vas_bench_joy");
            fillVasya(33, 1, "Скоро вступительные, а мне еще столько учить.. Но пока ты со мной - ничего не страшно!", "vas_bench_sad");
            fillVasya(34, 1, "Представляешь, колибри — единственная птица, которая может летать задом наперед", "vas_bench_idea");

        }
    }

    //Люда






    public void fillLudaAll(int level) {
        if (level == 1) {
            fillVasya(1, 1, "Я с самого детства мечтала иметь огромный сад", "luda_demo");
            fillVasya(2, 1, "Работы предстоит много, но вместе мы справимся", "luda_demo");
            fillVasya(3, 1, "Всегда мечтала об огромном саде. Ты мне поможешь?", "luda_demo");
            fillVasya(4, 1, "Поливаем, поливаем, лунка за лункой", "luda_demo");

        }
        else if (level == 2) {


        }
        else if (level == 3){


        }
    }
    /////

    public boolean fillAchievement(int id, String title, String text, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ACHIEVEMENT_ID, id);
        cv.put(COLUMN_ACHIEVEMENT_TITLE, title);
        cv.put(COLUMN_ACHIEVEMENT_TEXT, text);
        cv.put(COLUMN_ACHIEVEMENT_STATUS, status);
        long insert = db.insert(ACHIEVEMENTS_TABLE, null, cv);
        return insert != -1;
    }

    public List<AchievementsData> getAchievements() {
        List<AchievementsData> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + ACHIEVEMENTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String text = cursor.getString(2);
                int status = cursor.getInt(3);

                AchievementsData newStep = new AchievementsData(id, title, text, status);
                returnList.add(newStep);
            }
            while (cursor.moveToNext());
        } else {
            //failure
        }
        cursor.close();
        db.close();
        return returnList;
    }


    public void fillAchievementsAll() {
        fillAchievement(1, "Первые шаги", "Добавьте свое первое задание", 0);
        fillAchievement(2, "Выполните задание", "Выполните свое первое задание", 0);
        fillAchievement(3, "Просмотрите статистику", "Просмотрите статистику по конкретному заданию", 0);
        fillAchievement(4, "Поговорите", "Просмотрите реплики помощника 10 раз", 0);
        fillAchievement(5, "Побеседуйте", "Просмотрите реплики помощника 500 раз", 0);
        fillAchievement(6, "3 в ряд", "Выполняйте все задания 3 дня подряд", 0);
        fillAchievement(7, "10 в ряд", "Выполняйте все задания 10 дней подряд", 0);
        fillAchievement(8, "Цезарь", "Добавьте 5 заданий", 0);
        fillAchievement(9, "Всегда можно передумать", "Отмените выполнение задания", 0);
        fillAchievement(10, "Планы изменились", "Измените детали задания", 0);
        fillAchievement(11, "Ответственный", "Достигните цели без единого пропуска", 0);
        fillAchievement(12, "Первый результат", "Завершите задание не раньше, чем через 10 дней после начала", 0);

    }
    public void finishAchievement (int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "";
                queryString = " UPDATE " + ACHIEVEMENTS_TABLE + " SET " + COLUMN_ACHIEVEMENT_STATUS + " = 1 WHERE " + COLUMN_ACHIEVEMENT_ID + " = " + id;
                db.execSQL(queryString);
    }

    public boolean achievementsisEmpty() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ACHIEVEMENTS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() <= 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (Exception ex) {
            cursor.close();
            return true;
        }
    }


    public PhraseData randomPhrase(int level) {
        String phrase = "errorbegin";
        SQLiteDatabase db = this.getReadableDatabase();
        PhraseData phraseData = new PhraseData(0,1,"","");
        String query = "SELECT * FROM " + VASYA_TABLE + " WHERE " + COLUMN_VASYA_LEVEL + " = " + level + " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                phrase = cursor.getString(2);
                String emotion = cursor.getString(3);
                phraseData = new PhraseData(id, level,phrase,emotion);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return phraseData;
    }




    public boolean DoesExist(StepsData stepsData) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + STEPS_STATISTICS_TABLE + " WHERE " + COLUMN_STEP_STATISTICS_ID + " = " + stepsData.getStepId() + " AND " + COLUMN_STEP_STATISTICS_ACTION_DATE + " = '" + dateFormat.format(date) + "' AND " + COLUMN_STEP_STATISTICS_STATUS + " = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            } else {
                cursor.close();
                return true;
            }
        } catch (Exception ex) {
            cursor.close();
            return false;
        }
    }


    public boolean VasyaisEmpty(int level) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + VASYA_TABLE + " WHERE "+ COLUMN_VASYA_LEVEL + " = "+ level;
        Cursor cursor = db.rawQuery(query, null);
        cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            } else {
                cursor.close();
                return true;
            }
        } catch (Exception ex) {
            cursor.close();
            return false;
        }
    }
}


