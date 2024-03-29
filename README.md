# IngSW2023
<h1 align="center">
  <br>
  <a href="https://www.craniocreations.it/prodotto/my-shelfie"><img src="https://i.ibb.co/whDNK9g/banner.png" alt="MyShelfie" width="900"></a>
  <br>
  Project PoliMi for ing-sw
  <br>
</h1>
<h3 align="center">Grade 30L/30</h3>
<div align="center">Presentation date: 12/06/2023, Official deadline: 03/07/2023 (presented 1 month earlier)</div><br>

<h4 align="center">A distribuited version of the game MyShelfie  made by <br><br>
  <a href="https://github.com/TheFedelino01" target="_blank">Saccani Federico</a><br><br>
  <a href="https://github.com/francescospangaro" target="_blank">Spangaro Francesco</a><br><br>
  <a href="https://github.com/kessal001" target="_blank">Pedersoli Luca</a> <br><br>
  <a href="https://github.com/Luc076547" target="_blank">Sanvito Luca</a></h4>

<div align="center">
  
   | Coverage | Branches  |
|:--------|:------|
| ![Coverage](.github/badges/jacoco.svg)   | ![Branches](.github/badges/branches.svg)   |
  
  Code Coverage autogenerated using [JaCoCo](https://www.eclemma.org/jacoco/)  (autogenerated badges from [here](https://github.com/cicirello/jacoco-badge-generator))<br>

</div>


## What we have implemented <img src="https://i.ibb.co/RzyJZXm/imp.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">

We have `implemented`, in addiction to the `Game Specific` and `Game Agnostic` requirements,  the following advanced features:
   | Feature | Implemented  |
|:--------|:----|
| Multiple Games   | :heavy_check_mark:    |
| Resilience to clients disconnections  | :heavy_check_mark:    |
| Chat  | :heavy_check_mark:    |
| Socket and RMI  | :heavy_check_mark:    |
| Complete rules  | :heavy_check_mark:    |
| TUI + GUI  | :heavy_check_mark:    |
| Server disconnections  | :x:    |

Requirements: <a href="https://github.com/TheFedelino01/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/blob/main/Documents/Requirements/requirements.pdf">requirements.pdf</a> <br>
Rulebook: <a href="https://github.com/TheFedelino01/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/blob/main/Documents/Requirements/MyShelfie_Rulebook_ITA.pdf">rulebook.pdf</a> <br>
Official Site: <a href="https://www.craniocreations.it/prodotto/my-shelfie">producer site</a>

# How to Use <img src="https://i.ibb.co/QHmskqv/run.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">
**From Github: (for windows users)**<br>
`Option1:`
1. Go to releases
2. Download the latest release published
3. Double click on the .exe file to run `MyShelfieServer.exe` and `MyShelfieClient.exe`

`Option2:`
1. Go to /Deployable folder
2. Download the .exe files of the server and the client

**From Repo clone: (for mac and windows users)**
1. Go to /Deployable and download both files  
2. Open `CMD` and navigate to C:/[dir]/[folder with the jar files]  
3. From here, just type in the `CMD`:  
   -> `java -jar softeng-gc04-1.0-SNAPSHOT-client.jar` (to run the clients)<br>
   -> `java -jar softeng-gc04-1.0-SNAPSHOT-server.jar` (to run the server)  

  
**From Intellij: (for developers)**
1. Intellij: maven `menù -> MyShelfie -> Lifecycle -> Clean` 
2. Intellij: maven  `menù -> MyShelfie -> Lifecycle -> Package` 
3. Open `CMD`, on project directory:   
   -> C:/[dir]/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/MyShelfie/target  
   From here:  
   -> `java -jar softeng-gc04-1.0-SNAPSHOT-client.jar` (to run the clients)<br>
   -> `java -jar softeng-gc04-1.0-SNAPSHOT-server.jar` (to run the server)
Repeat from (1) each time the code is modified  
  

# Fix issue or download JAVA SE 19
We have created an easy cmd script (for windows) that automatically perform all the following instructions.<br>
Download the <a href="https://github.com/TheFedelino01/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/blob/main/Deployable/easy-install-java-myshelfie.cmd">easy-install-java-myshelfie.cmd</a> file from the repo and execute it has Administrator!<br><br>

Otherwise, try manually these steps manually:<br>
In order to run the game properly, you need install <a href="https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html">Java SE Development Kit 19.0.2</a><br>
When installed, you need to add the following Environment Variables in windows (search bar: "env" and press enter then click on "Environment variables"):<br>
<div>In the list of "Environment variables of the user" add the following:</div>

  1. `JAVA_HOME C:\Program Files\Java\jdk-19`<br>
  2. `JDK_HOME C:\Program Files\Java\jdk-19`<br>
<div>In the list of "System variables" add the following:</div>

  1. `CLASSPATH C:\Program Files\Java\jdk-19\lib`

# How generate Code Coverage <img src="https://i.ibb.co/4PYzV7D/cov.png" align="right" alt="logo" width="130" height = "139" style = "border: none; float: right;">
To generate the Code Coverage:  
1. Intellij: maven `menù -> MyShelfie -> Lifecycle -> Clean` 
2. Intellij: maven `menù -> MyShelfie -> Lifecycle -> Test`  
Code Coverage will be generated in: <br>
 -> `C:/[dir]/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/MyShelfie/target/site/jacoco/index.html`<br><br>
Open the file to see a more detailed view on the project's code coverage      

# Game Screenshot
<img src="https://i.postimg.cc/fRjV3Gf4/menu.png" alt="logo" width="600" heigth="300" float="center">
<img src="https://i.ibb.co/ZJ0yHTQ/nickname.png" alt="logo" width="600" heigth="300" float="center">
<img src="https://i.postimg.cc/MTXHFv2x/lobby.png" alt="logo" width="600" heigth="300" float="center">
<img src="https://i.postimg.cc/sX8xdssW-/game.png" alt="logo" width="600" heigth="300" float="center">


## Other Features
* Full screen mode responsive
* Sound effects
* Cross platform
  - Windows and macOS ready.

## Download

You can [download](https://github.com/TheFedelino01/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/releases/) the latest installable version of MyShelfie for Windows and macOS.


## Credits

This software uses the following open source packages:

- [Json-Simple](https://code.google.com/archive/p/json-simple/)
- [OpenJfx](https://openjfx.io/)
- [JUnit](https://junit.org/junit5/)
- [Gson](https://github.com/google/gson)
- [Jansi](https://fusesource.github.io/jansi/)

## Disclaimer
NOTA: My Shelfie è un gioco da tavolo sviluppato ed edito da Cranio Creations Srl. I contenuti grafici di questo progetto riconducibili al prodotto editoriale da tavolo sono utilizzati previa approvazione di Cranio Creations Srl a solo scopo didattico. È vietata la distribuzione, la copia o la riproduzione dei contenuti e immagini in qualsiasi forma al di fuori del progetto, così come la redistribuzione e la pubblicazione dei contenuti e immagini a fini diversi da quello sopracitato. È inoltre vietato l'utilizzo commerciale di suddetti contenuti.
