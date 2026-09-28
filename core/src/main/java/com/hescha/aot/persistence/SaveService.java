package com.hescha.aot.persistence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.hescha.aot.data.CharacterCatalog;
import com.hescha.aot.data.CharacterDef;

public final class SaveService {
    private final Preferences preferences = Gdx.app.getPreferences("aot_rebuild_prefs");
    private final CharacterCatalog characters;

    public SaveService(CharacterCatalog characters) {
        this.characters = characters;
        migrateLegacy();
    }

    private void migrateLegacy() {
        if (preferences.getBoolean("legacy_migrated", false)) return;
        Preferences old = Gdx.app.getPreferences("AOTGame-2023-17-10");
        int legacyMoney = old.getInteger("money", 0);
        if (legacyMoney > coins()) preferences.putInteger("total_coins", legacyMoney);
        int oldBest = old.getInteger("maxMurderCount", 0);
        if (oldBest > preferences.getInteger("legacy_best_kills", 0))
            preferences.putInteger("legacy_best_kills", oldBest);
        for (CharacterDef character : characters.all()) {
            if (old.getBoolean(character.id, false))
                preferences.putBoolean("char_" + character.id, true);
        }
        preferences.putBoolean("char_" + characters.defaultCharacter().id, true)
                .putBoolean("legacy_migrated", true).flush();
    }

    public int coins() {
        return preferences.getInteger("total_coins", 0);
    }

    public void addCoins(int amount) {
        preferences.putInteger("total_coins", Math.max(0, coins() + amount)).flush();
    }

    public boolean unlocked(CharacterDef character) {
        return character.price == 0 || character.id.equals(characters.defaultCharacter().id)
                || preferences.getBoolean("char_" + character.id, false);
    }

    public boolean buy(CharacterDef character) {
        if (unlocked(character)) return true;
        if (coins() < character.price) return false;
        preferences.putInteger("total_coins", coins() - character.price)
                .putBoolean("char_" + character.id, true).flush();
        return true;
    }

    public String selectedCharacter() {
        CharacterDef selected = characters.find(preferences.getString("selected_character", characters.defaultCharacter().id));
        return selected != null && unlocked(selected) ? selected.id : characters.defaultCharacter().id;
    }

    public void select(String id) {
        CharacterDef character = characters.find(id);
        if (character != null && unlocked(character))
            preferences.putString("selected_character", id).flush();
    }

    public float bestSurvival() {
        return preferences.getFloat("best_survival_time", 0f);
    }

    public void saveBest(float time) {
        if (time > bestSurvival()) preferences.putFloat("best_survival_time", time).flush();
    }

    public boolean tutorialSeen() {
        return preferences.getBoolean("tutorial_seen", false);
    }

    public void setTutorialSeen() {
        preferences.putBoolean("tutorial_seen", true).flush();
    }
}
