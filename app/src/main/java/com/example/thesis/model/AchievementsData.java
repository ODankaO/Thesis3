package com.example.thesis.model;

public class AchievementsData {

    private int achievement_id;
    private String achievement_title;
    private String achievement_text;
    private int achievement_status;


    public AchievementsData(int achievement_id, String achievement_title, String achievement_text, int achievement_status) {
        this.achievement_id = achievement_id;
        this.achievement_title = achievement_title;
        this.achievement_text = achievement_text;
        this.achievement_status = achievement_status;


    }

    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public String getAchievement_title() {
        return achievement_title;
    }

    public void setAchievement_title(String achievement_title) {
        this.achievement_title = achievement_title;
    }

    public String getAchievement_text() {
        return achievement_text;
    }

    public void setAchievement_text(String achievement_text) {
        this.achievement_text = achievement_text;
    }

    public int getAchievement_status() {
        return achievement_status;
    }

    public void setAchievement_status(int achievement_status) {
        this.achievement_status = achievement_status;
    }
}
