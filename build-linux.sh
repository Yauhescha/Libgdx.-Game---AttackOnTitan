#!/usr/bin/env bash
set -euo pipefail
if command -v gradle >/dev/null; then exec gradle :android:assembleDebug; fi
echo "Install Gradle 8.11.1 (or open the project in Android Studio) and rerun."
exit 1
