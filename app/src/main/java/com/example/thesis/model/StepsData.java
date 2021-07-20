package com.example.thesis.model;

public class StepsData {

    private int stepId;
    private String stepName;
    private String description;
    private String color;
    private String stepStart;
    private String stepFinish;
    private String stepAppearance;
    private int stepTimes;


    public StepsData(int stepId, String stepName, String description, String color, String stepStart, String stepFinish, String stepAppearance, int stepTimes) {
        this.stepId = stepId;
        this.stepName = stepName;
        this.description = description;
        this.color = color;
        this.stepStart = stepStart;
        this.stepFinish = stepFinish;
        this.stepAppearance = stepAppearance;
        this.stepTimes = stepTimes;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getStepStart() {
        return stepStart;
    }

    public void setStepStart(String stepStart) {
        this.stepStart = stepStart;
    }

    public String getStepFinish() {
        return stepFinish;
    }

    public void setStepFinish(String stepFinish) {
        this.stepFinish = stepFinish;
    }

    public String getStepAppearance() {
        return stepAppearance;
    }

    public void setStepAppearance(String stepAppearance) {
        this.stepAppearance = stepAppearance;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStepTimes() {
        return stepTimes;
    }

    public void setStepTimes(int stepTimes) {
        this.stepTimes = stepTimes;
    }
}
