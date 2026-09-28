# Проверка перед выдачей

- Проверена грамматика всех Java-файлов (`javac`): синтаксических ошибок нет.
- Проверено, что в архив не попали `build/`, `.gradle/`, `local.properties`, `.so`, `.ttf/.otf`.
- Полный `:android:assembleDebug` в среде ChatGPT не выполнялся: в контейнере нет Android SDK/Gradle
  dependencies. Поэтому первый реальный Android build нужно сделать у себя через Android Studio или
  `build-windows.bat`.
- После сборки для 16 KB рекомендуется отдельно проверить APK командой
  `zipalign -c -P 16 -v 4 android-debug.apk`.
