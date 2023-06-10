@echo off

REM Chiedi all'utente se desidera scaricare e installare Java
set /p installJava="Desideri scaricare e installare Java? (S/N): "
if /i "%installJava%"=="S" (
    REM Download JDK 19.0.2
    curl -o jdk-19.0.2_windows-x64_bin.msi -L "https://download.oracle.com/java/19/archive/jdk-19.0.2_windows-x64_bin.msi"

    REM Install JDK 19.0.2
    msiexec /i jdk-19.0.2_windows-x64_bin.msi
)

REM Aggiungi le variabili d'ambiente dell'utente
echo Aggiunta delle variabili d'ambiente dell'utente...

REM Aggiungi JAVA_HOME
setx JAVA_HOME "C:\Program Files\Java\jdk-19"

REM Aggiungi JDK_HOME
setx JDK_HOME "C:\Program Files\Java\jdk-19"

echo Variabili d'ambiente dell'utente aggiunte correttamente.

REM Aggiungi la variabile CLASSPATH alle variabili di sistema
echo Aggiunta della variabile CLASSPATH alle variabili di sistema...

REM Leggi il valore corrente della variabile CLASSPATH delle variabili di sistema
for /f "tokens=2*" %%i in ('reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v CLASSPATH') do set existingClasspath=%%j

REM Aggiungi la directory del JDK 19.0.2 al valore esistente della variabile CLASSPATH delle variabili di sistema
set updatedClasspath=%existingClasspath%;C:\Program Files\Java\jdk-19\lib

REM Imposta il nuovo valore della variabile CLASSPATH delle variabili di sistema
reg add "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v CLASSPATH /t REG_EXPAND_SZ /d "%updatedClasspath%" /f

echo Variabile CLASSPATH aggiunta alle variabili di sistema correttamente.

REM Visualizza un messaggio e attendi l'input dell'utente per chiudere la console
echo Premi un tasto per chiudere...
pause >nul
