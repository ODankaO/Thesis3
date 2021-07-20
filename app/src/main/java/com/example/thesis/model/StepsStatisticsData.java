package com.example.thesis.model;

public class StepsStatisticsData {

    private int stepId;
    private String stepActionDate;
    private int stepStatus;
    private int stepCount;

    public StepsStatisticsData(int stepId, String stepActionDate, int stepStatus, int stepCount) {
        this.stepId = stepId;
        this.stepActionDate = stepActionDate;
        this.stepStatus = stepStatus;
        this.stepCount = stepCount;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getStepActionDate() {
        return stepActionDate;
    }

    public void setStepActionDate(String stepActionDate) {
        this.stepActionDate = stepActionDate;
    }

    public Integer getStepId() {
        return stepId;
    }

    public int getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(int stepStatus) {
        this.stepStatus = stepStatus;
    }
}
