@REM ═══════════════════════════════════════════════════════════════════════════
@REM Maven Wrapper Script (Windows)
@REM ═══════════════════════════════════════════════════════════════════════════
@REM
@REM Este script descarga y ejecuta la versión correcta de Maven automáticamente.
@REM Uso: mvnw.cmd clean package
@REM
@REM ═══════════════════════════════════════════════════════════════════════════

@echo off
setlocal

set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties

@REM Si no existe el wrapper jar, descargarlo
if not exist "%MAVEN_WRAPPER_JAR%" (
    echo Maven Wrapper not found. Please run from a Unix environment first to download.
    exit /b 1
)

@REM Ejecutar Maven
java ^
    -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" ^
    -Dmaven.home="%MAVEN_PROJECTBASEDIR%\.mvn" ^
    -classpath "%MAVEN_WRAPPER_JAR%" ^
    org.apache.maven.wrapper.MavenWrapperMain %*

endlocal
