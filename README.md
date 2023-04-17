# IngSW2023
Prova Finale Ingegneria del Software AA 2022-2023 (Cugola Gianpaolo)

Per runnare e testare il gioco:  
1. Intellij: menù maven -> MyShelfie -> Lifecycle -> Verify  
  
2. Aprire CMD    
   Portarsi sulla directory del progetto:   
   -> C:/[dir]/ing-sw-2023-saccani-spangaro-sanvito-pedersoli/MyShelfie/target  
   Eseguire da qui  
   -> java -jar softeng-gc04-1.0-SNAPSHOT-client.jar (per i client)  
   -> java -jar softeng-gc04-1.0-SNAPSHOT-server.jar (per i server)  
   
Ripetere da (1) ogni volta che si modifica il codice per aggiornare i jar

ATTENZIONE  
  
Quando si va in fase di debugging, ci sono problemi con la lettura da file fatta in fase di run da console,  
per ovviare a questi problemi, guardare il codice della classe player righe 94, 123 e 136   
