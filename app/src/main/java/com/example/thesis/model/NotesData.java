package com.example.thesis.model;

public class NotesData {

    private int stepId;
    private String time;


    public NotesData(int stepId, String time) {
        this.stepId = stepId;
        this.time = time;

    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
