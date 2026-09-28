package com.hescha.aot.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uses libGDX internal files, so the same folder layout works inside an Android APK.
 */
final class CharacterAssetFiles {
    private static final Pattern NUMBER = Pattern.compile("\\d+");

    private CharacterAssetFiles() {
    }

    static String[] images(String directory) {
        return discover(directory, false, null);
    }

    static String[] sounds(String directory, String excludedPath) {
        return discover(directory, true, excludedPath);
    }

    private static String[] discover(String directory, boolean audio, String excludedPath) {
        List<FileHandle> files = new ArrayList<>();
        for (FileHandle file : Gdx.files.internal(directory).list()) {
            String ext = file.extension().toLowerCase(Locale.ROOT);
            boolean include = audio ? ext.equals("wav") || ext.equals("ogg") || ext.equals("mp3") : ext.equals("png");
            if (include && !file.path().equals(excludedPath)) files.add(file);
        }
        // 1.png, 2.png, 10.png; also preserves old names such as attack (0).png.
        files.sort(Comparator.comparingInt((FileHandle file) -> frameNumber(file.nameWithoutExtension()))
                .thenComparing(file -> file.name()));
        String[] paths = new String[files.size()];
        for (int i = 0; i < files.size(); i++) paths[i] = files.get(i).path();
        return paths;
    }

    private static int frameNumber(String name) {
        Matcher matcher = NUMBER.matcher(name);
        int result = Integer.MAX_VALUE;
        while (matcher.find()) result = Integer.parseInt(matcher.group());
        return result;
    }
}
