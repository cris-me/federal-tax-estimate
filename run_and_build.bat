@echo off
echo 🔨 Building the Spring Boot application...

:: Build the project
call mvnw.cmd clean install

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Build failed. Aborting run.
    exit /b %ERRORLEVEL%
)

echo ✅ Build successful.
echo 🚀 Running the Spring Boot application...

:: Run the application
call mvnw.cmd spring-boot:run
