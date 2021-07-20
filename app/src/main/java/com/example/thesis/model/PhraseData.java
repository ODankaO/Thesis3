package com.example.thesis.model;

public class PhraseData {

    private int stepId;
    private int level;
    private String phrase;
    private String emotion;

    public PhraseData(int stepId, int level, String phrase, String emotion) {
        this.stepId = stepId;
        this.level = level;
        this.phrase = phrase;
        this.emotion = emotion;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
