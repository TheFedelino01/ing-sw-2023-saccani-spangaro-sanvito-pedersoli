# IngSW2023
Prova Finale Ingegneria del Software AA 2022-2023 (Cugola Gianpaolo)  
  
Abbiamo implementato:  
 -> Socket ed RMI  
 -> GUI e TUI  
 -> Chat  
 -> Disconnessione lato client  
 -> Partite multiple  
 -> Regole complete  
   
Coverage del codige (badge autogenerati da [qui](https://github.com/cicirello/jacoco-badge-generator)) 
  
![Coverage](.github/badges/jacoco.svg)  
![Branches](.github/badges/branches.svg)  
  
  
La code coverage del progetto è autogenerata anch'essa, utilizzando [JaCoCo](https://www.eclemma.org/jacoco/)  
    
Per runnare e testare il gioco:  
1. Intellij: menù maven -> MyShelfie -> Lifecycle -> Clean  
2. Intellij: menù maven -> MyShelfie -> Lifecycle -> Package  
3. Aprire CMD, portarsi sulla directory del progetto:   
   -> C:/[dir]/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/MyShelfie/target  
   Eseguire da qui:  
   -> java -jar softeng-gc04-1.0-SNAPSHOT-client.jar (per i client)  
   -> java -jar softeng-gc04-1.0-SNAPSHOT-server.jar (per il server)  
   
Ripetere da (1) ogni volta che si modifica il codice per aggiornare i jar  

Per generare la Code Coverage del progetto:  
1. Intellij: menù maven -> MyShelfie -> Lifecycle -> Clean  
2. Intellij: menù maven -> MyShelfie -> Lifecycle -> Test  
La code coverage viene generata nella cartella:  
 -> C:/[dir]/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/MyShelfie/target/site/jacoco/index.html
      
      


