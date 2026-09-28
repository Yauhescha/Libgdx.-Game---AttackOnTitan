package com.hescha.aot.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Loaded once on startup, after libGDX has initialized its file API.
 */
public final class CharacterCatalog {
    private final Map<String, CharacterDef> byId = new LinkedHashMap<>();
    private final List<CharacterDef> characters;
    private final CharacterDef defaultCharacter;
    public final String defaultAttackSound;
    public final String[] defaultDeathSounds, defaultAttackEffectFrames;
    public final float defaultAttackEffectFrameDuration;

    public CharacterCatalog() {
        JsonValue config = new JsonReader().parse(Gdx.files.internal("config/characters.json"));
        for (JsonValue entry : config.get("characters")) {
            if (!entry.getBoolean("enabled", true)) continue;
            CharacterDef character = new CharacterDef(entry);
            if (byId.put(character.id, character) != null)
                throw new GdxRuntimeException("Duplicate character id in config/characters.json: " + character.id);
        }
        if (byId.isEmpty())
            throw new GdxRuntimeException("config/characters.json needs at least one enabled character");
        characters = Collections.unmodifiableList(new ArrayList<>(byId.values()));
        defaultCharacter = byId.getOrDefault(config.getString("defaultCharacter", characters.get(0).id), characters.get(0));
        defaultAttackSound = config.getString("defaultAttackSound");
        defaultDeathSounds = config.get("defaultDeathSounds").asStringArray();
        defaultAttackEffectFrames = config.get("defaultAttackEffectFrames").asStringArray();
        defaultAttackEffectFrameDuration = config.getFloat("defaultAttackEffectFrameDuration", .1f);
    }

    public List<CharacterDef> all() {
        return characters;
    }

    public CharacterDef defaultCharacter() {
        return defaultCharacter;
    }

    public CharacterDef find(String id) {
        return byId.get(id);
    }

    public CharacterDef byId(String id) {
        return byId.getOrDefault(id, defaultCharacter);
    }
}
