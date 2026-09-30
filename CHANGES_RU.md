# Что исправлено

1. **Газ** — постоянный базовый расход + дополнительный расход при активном ODM-hook.
2. **Баллоны** — периодически появляются в мире, подбор восполняет газ.
3. **Полное 2D ODM-движение** — anchor можно ставить выше/ниже/слева/справа; персонажа тянет к точке.
4. **Затылок титана** — у каждого титана отдельная nape/neck hitbox; попадание в тело не считается убийством.
5. **Меню** — Play / Characters-Shop / Tutorial / Exit.
6. **Персонажи** — Armin, Bertholdt, Eren, Annie, Mikasa; покупка и выбор сохраняются.
7. **Режимы** — Training, Expedition 90s, Survival, Combo 60s.
8. **Сохранения** — монеты, лучший survival time, выбор/покупки; предусмотрена миграция legacy preferences.
9. **Android 16 KB** — удалён runtime gdx-freetype и старые вручную вложенные native-библиотеки; обновлены Android Gradle Plugin/libGDX, отключён legacy JNI packaging.
10. **Проект очищен** — нет `build/`, `.transforms`, `local.properties`, IDE-кэшей и старых бинарных `.so`.
