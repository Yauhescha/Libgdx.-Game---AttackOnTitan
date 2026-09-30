package com.hescha.aot.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.hescha.aot.data.CharacterDef;
import com.hescha.aot.data.CharacterCatalog;
import com.hescha.aot.domain.EnemyType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameAssets implements Disposable {
    private final CharacterCatalog characters;
    private final Map<String, Texture> textureCache = new HashMap<>();
    private final Map<String, Sound> soundCache = new HashMap<>();
    private final List<Texture> textures = new ArrayList<>();
    private final List<Sound> sounds = new ArrayList<>();
    private final Map<String, Texture> stands = new HashMap<>();
    private final Map<String, Animation<Texture>> attacks = new HashMap<>();
    private final Map<String, Animation<Texture>> deaths = new HashMap<>();
    private final Map<String, Animation<Texture>> attackEffects = new HashMap<>();
    private final Map<String, Sound> attackSounds = new HashMap<>();
    private final Map<String, Sound[]> deathSounds = new HashMap<>();
    private final Map<EnemyType, Animation<Texture>> titans = new EnumMap<>(EnemyType.class);
    private final List<Texture> hairs = new ArrayList<>();

    public final Texture street = texture("street.png");
    // Replace this PNG to change the pickup artwork. No Java changes needed.
    public final Texture gasCanister = texture("pickups/gas-canister.png");
    public final BitmapFont font = new BitmapFont(Gdx.files.internal("ui-font.fnt"));
    public final Music theme = Gdx.audio.newMusic(Gdx.files.internal("music/theme.mp3"));
    private final Sound defaultAttackSound;
    private final Sound[] defaultDeathSounds;
    private final Animation<Texture> defaultAttackEffect;
    public final Animation<Texture> flightSmoke = sequence(
            "effect/flying/flying (%d).png", 1, 4, .1f, false);
    public final Animation<Texture> evaporation = sequence(
            "effect/dissapearing/%d.png", 1, 14, .08f, false);

    public GameAssets(CharacterCatalog characters) {
        this.characters = characters;
        defaultAttackSound = characters.defaultAttackSound.isEmpty() ? null : sound(characters.defaultAttackSound);
        defaultDeathSounds = loadSounds(characters.defaultDeathSounds);
        defaultAttackEffect = animation(characters.defaultAttackEffectFrameDuration, false, characters.defaultAttackEffectFrames);
        font.getData().setScale(.72f);
        theme.setLooping(true);
        loadCharacters();
        loadTitans();
    }

    private Texture texture(String path) {
        Texture cached = textureCache.get(path);
        if (cached != null) return cached;
        Texture result = new Texture(Gdx.files.internal(path));
        result.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textures.add(result);
        textureCache.put(path, result);
        return result;
    }

    private Sound sound(String path) {
        Sound cached = soundCache.get(path);
        if (cached != null) return cached;
        Sound result = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.add(result);
        soundCache.put(path, result);
        return result;
    }

    private Animation<Texture> animation(float frameTime, boolean loop, String... paths) {
        Array<Texture> frames = new Array<>();
        for (String path : paths) frames.add(texture(path));
        return new Animation<>(frameTime, frames,
                loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
    }

    private Animation<Texture> sequence(String pattern, int first, int count,
                                        float frameTime, boolean loop) {
        String[] paths = new String[count];
        for (int i = 0; i < count; i++) paths[i] = pattern.replace("%d", Integer.toString(first + i));
        return animation(frameTime, loop, paths);
    }

    private void loadCharacters() {
        for (CharacterDef character : characters.all()) {
            Texture stand = texture(character.assetPath(character.stand));
            stands.put(character.id, stand);
            attacks.put(character.id, characterAnimation(character, "attack", character.attackFrames,
                    character.attackFrameDuration, stand));
            deaths.put(character.id, characterAnimation(character, "dead", character.deathFrames,
                    character.deathFrameDuration, stand));
            String[] effectPaths = framePaths(character, "attackEffect", character.attackEffectFrames);
            attackEffects.put(character.id, effectPaths.length == 0 ? defaultAttackEffect
                    : animation(character.attackEffectFrameDuration, false, effectPaths));

            String ownAttackPath = character.assetPath(character.attackSound == null || character.attackSound.isEmpty()
                    ? "sound/attack.wav" : character.attackSound);
            Sound attackClip;
            if (character.attackSound != null) {
                attackClip = character.attackSound.isEmpty() ? null : sound(ownAttackPath);
            } else {
                attackClip = Gdx.files.internal(ownAttackPath).exists() ? sound(ownAttackPath) : defaultAttackSound;
            }
            attackSounds.put(character.id, attackClip);
            String[] deathPaths = character.deathSounds == null
                    ? CharacterAssetFiles.sounds(character.assetPath("sound"), ownAttackPath)
                    : relativePaths(character, character.deathSounds);
            deathSounds.put(character.id, character.deathSounds == null && deathPaths.length == 0
                    ? defaultDeathSounds : loadSounds(deathPaths));
        }
    }

    private Animation<Texture> characterAnimation(CharacterDef character, String directory, String[] explicitFrames,
                                                   float frameDuration, Texture fallback) {
        String[] paths = framePaths(character, directory, explicitFrames);
        return paths.length == 0 ? new Animation<Texture>(frameDuration, fallback)
                : animation(frameDuration, false, paths);
    }

    private String[] framePaths(CharacterDef character, String directory, String[] explicitFrames) {
        return explicitFrames == null ? CharacterAssetFiles.images(character.assetPath(directory))
                : relativePaths(character, explicitFrames);
    }

    private String[] relativePaths(CharacterDef character, String[] paths) {
        String[] result = new String[paths.length];
        for (int i = 0; i < paths.length; i++) result[i] = character.assetPath(paths[i]);
        return result;
    }

    private Sound[] loadSounds(String[] paths) {
        Sound[] result = new Sound[paths.length];
        for (int i = 0; i < paths.length; i++) result[i] = sound(paths[i]);
        return result;
    }

    private void loadTitans() {
        titans.put(EnemyType.FAT1, animation(.28f, true,
                "enemy/fat1/fat1 (1).png", "enemy/fat1/fat1 (3).png"));
        titans.put(EnemyType.FAT2, animation(.28f, true,
                "enemy/fat2/fat2 (1).png", "enemy/fat2/fat2 (3).png"));
        titans.put(EnemyType.FAT3, animation(.28f, true,
                "enemy/fat3/fat3 (1).png", "enemy/fat3/fat3 (3).png"));
        for (int i = 1; i <= 4; i++) {
            titans.put(EnemyType.valueOf("MAN" + i), sequence("enemy/man" + i + "/%d.png", 1, 2, .28f, true));
            titans.put(EnemyType.valueOf("WOMAN" + i), sequence("enemy/woman" + i + "/%d.png", 1, 2, .28f, true));
        }
        for (int i = 1; i <= 18; i++) hairs.add(texture("enemy/hair/" + i + ".png"));
    }

    public Texture character(String id) { return stands.get(characters.byId(id).id); }
    public Animation<Texture> characterAttack(String id) { return attacks.get(characters.byId(id).id); }
    public Animation<Texture> characterDeath(String id) { return deaths.get(characters.byId(id).id); }
    public Animation<Texture> characterAttackEffect(String id) { return attackEffects.get(characters.byId(id).id); }
    public Animation<Texture> titan(EnemyType type) { return titans.get(type); }
    public Texture hair(int i) { return hairs.get(Math.floorMod(i, hairs.size())); }
    public int hairCount() { return hairs.size(); }

    public void playAttack(String id) {
        Sound clip = attackSounds.get(characters.byId(id).id);
        if (clip != null) clip.play(.65f);
    }

    public void playDeath(String id) {
        Sound[] clips = deathSounds.get(characters.byId(id).id);
        if (clips.length > 0) clips[MathUtils.random(clips.length - 1)].play(.8f);
    }

    public void playMusic() { if (!theme.isPlaying()) theme.play(); }
    public void stopMusic() { theme.stop(); }
    public void stopEffects() { for (Sound sound : sounds) sound.stop(); }

    @Override public void dispose() {
        theme.dispose();
        font.dispose();
        for (Sound sound : sounds) sound.dispose();
        for (Texture texture : textures) texture.dispose();
    }
}
