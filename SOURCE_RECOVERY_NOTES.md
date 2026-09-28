# Source recovery notes

Исходный `AOT.rar` содержал две разные стадии проекта: старые Java-исходники `com.hescha.game` и
более новую скомпилированную ветку `com.hescha.aot` в build/.transforms, но Java-файлов новой ветки
в `core/src/main/java/com/hescha/aot` не было.

Поэтому этот архив не является простым копированием исходного RAR. Новая ветка восстановлена в
нормальные Java-исходники и приведена к единой структуре Gradle. Старые `build/`, `.transforms`,
`android/libs/*.so`, `local.properties` и runtime `gdx-freetype` намеренно не переносятся.

Ключевые механики: постоянный расход газа + расход при ODM, подбор газа, 2D hook movement, отдельная
nape hitbox, магазин/выбор персонажей, 4 режима и миграция старого прогресса.
