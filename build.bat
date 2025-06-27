@echo off
echo 🔨 Building the Spring Boot application...

:: Make sure the wrapper is executable
:: Not needed on Windows, but leaving for reference

:: Run Maven build
call mvnw.cmd clean install

if %ERRORLEVEL% EQU 0 (
    echo ✅ Build successful.
) else (
    echo ❌ Build failed.
    exit /b %ERRORLEVEL%
)
