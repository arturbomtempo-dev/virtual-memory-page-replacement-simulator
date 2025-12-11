@echo off
REM ====================================================
REM Virtual Memory Paging Simulator - Build & Run
REM Platform: Windows
REM Usage: run.bat
REM ====================================================

setlocal enabledelayedexpansion

echo ==========================================
echo   Virtual Memory Paging Simulator
echo ==========================================
echo.

REM Ensure required directories exist
if not exist "bin" mkdir bin
if not exist "output" mkdir output

REM Check if input directory exists
if not exist "input" (
    echo [ERROR] 'input/' directory not found!
    echo Please create the 'input/' directory and add .txt test files.
    echo.
    pause
    exit /b 1
)

REM Compile Java sources
echo Compiling Java sources...

javac -d bin -encoding UTF-8 ^
    src\exception\*.java ^
    src\validation\*.java ^
    src\model\*.java ^
    src\parser\*.java ^
    src\policy\*.java ^
    src\app\*.java

if errorlevel 1 (
    echo [ERROR] Compilation failed!
    echo.
    pause
    exit /b 1
)

echo [OK] Compilation successful!
echo.

REM Run the simulator
echo Running simulator...
echo ------------------------------------------

java -Dfile.encoding=UTF-8 -cp bin app.Application

if errorlevel 1 (
    echo ------------------------------------------
    echo [ERROR] Execution failed!
    echo.
    pause
    exit /b 1
)

echo ------------------------------------------
echo [OK] Processing completed!
echo.
echo Output files generated in 'output\' directory:
dir /b output\*.txt 2>nul || echo   (no output files found)

echo.
echo ==========================================
echo.
pause
