package com.hescha.aot.data;

import com.badlogic.gdx.utils.JsonValue;
import java.util.Locale;

/** One entry in assets/config/characters.json. IDs also identify saved purchases. */
public final class CharacterDef {
    public final String id, title, assetDir, stand, attackSound;
    public final int price;
    public final float hookForce, moveFactor, attackRadius;
    public final float attackFrameDuration, deathFrameDuration, attackEffectFrameDuration;
    // null = discover files in the character folder; [] = deliberately no files.
    public final String[] attackFrames, deathFrames, attackEffectFrames, deathSounds;

    public CharacterDef(JsonValue json) {
        id = json.getString("id");
        title = json.getString("title", id);
        assetDir = json.getString("assetDir", id.toLowerCase(Locale.ROOT));
        price = Math.max(0, json.getInt("price", 0));
        hookForce = json.getFloat("hookForce", 1000f);
        moveFactor = json.getFloat("moveFactor", 1f);
        attackRadius = json.getFloat("attackRadius", 70f);
        stand = json.getString("stand", "stand.png");
        attackFrameDuration = json.getFloat("attackFrameDuration", .1f);
        deathFrameDuration = json.getFloat("deathFrameDuration", .1f);
        attackEffectFrameDuration = json.getFloat("attackEffectFrameDuration", .1f);
        attackFrames = optionalPaths(json, "attackFrames");
        deathFrames = optionalPaths(json, "deathFrames");
        attackEffectFrames = optionalPaths(json, "attackEffectFrames");
        attackSound = json.getString("attackSound", null);
        deathSounds = optionalPaths(json, "deathSounds");
    }

    public String assetPath(String relativePath) { return "player/" + assetDir + "/" + relativePath; }

    private static String[] optionalPaths(JsonValue json, String field) {
        JsonValue value = json.get(field);
        return value == null || value.isNull() ? null : value.asStringArray();
    }
}
