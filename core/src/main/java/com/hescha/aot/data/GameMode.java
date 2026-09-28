package com.hescha.aot.data;

public enum GameMode {
    TRAINING("Training", 0f, "No game over. Learn hooks and nape hits."),
    EXPEDITION("Expedition", 90f, "Survive for 90 seconds."),
    SURVIVAL("Survival", 0f, "Endless run. Difficulty keeps rising."),
    COMBO("Combo", 60f, "60 seconds. Chain nape kills for bonus coins.");
    public final String title;
    public final float duration;
    public final String description;

    GameMode(String title, float duration, String description) {
        this.title = title;
        this.duration = duration;
        this.description = description;
    }
}
