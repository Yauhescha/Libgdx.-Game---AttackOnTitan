@echo off
setlocal
cd /d "%~dp0"
set "AOT_GRADLE_VERSION=8.11.1"
set "AOT_GRADLE_DIR=%CD%\.gradle-dist\gradle-%AOT_GRADLE_VERSION%"
if not exist "%AOT_GRADLE_DIR%\bin\gradle.bat" (
  echo Downloading Gradle %AOT_GRADLE_VERSION%...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; New-Item -Force -ItemType Directory '.gradle-dist' | Out-Null; Invoke-WebRequest 'https://services.gradle.org/distributions/gradle-%AOT_GRADLE_VERSION%-bin.zip' -OutFile '.gradle-dist\gradle.zip'; Expand-Archive -Force '.gradle-dist\gradle.zip' '.gradle-dist'"
  if errorlevel 1 exit /b 1
)
call "%AOT_GRADLE_DIR%\bin\gradle.bat" :android:assembleDebug
exit /b %ERRORLEVEL%
