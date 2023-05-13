# IngSW2023
Prova Finale Ingegneria del Software AA 2022-2023 (Cugola Gianpaolo)

![Coverage](.github/badges/jacoco.svg)  
![Branches](.github/badges/branches.svg)  
  
  
  
Per generare la Code Coverage del progetto, runnare i test tramite il menù Maven -> MyShelfie\Lifecycle\test  
Per vedere la code coverage del progetto: MyShelfie\target\site\jacoco\index.html  
  
  
Per runnare e testare il gioco:  
1. Intellij: menù maven -> MyShelfie -> Lifecycle -> Verify  
  
2. Aprire CMD    
   Portarsi sulla directory del progetto:   
   -> C:/[dir]/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/MyShelfie/target  
   Eseguire da qui  
   -> java -jar softeng-gc04-1.0-SNAPSHOT-client.jar (per i client)  
   -> java -jar softeng-gc04-1.0-SNAPSHOT-server.jar (per i server)  
   
Ripetere da (1) ogni volta che si modifica il codice per aggiornare i jar  

NB: è stato confermato tramite slack che il gioco può runnare sul terminale che vogliamo noi  
    in fase di testing quindi, runnarlo da CMD, che permette di avere colori migliori, e una   
    funzione di resize del terminale funzionante
      
      


