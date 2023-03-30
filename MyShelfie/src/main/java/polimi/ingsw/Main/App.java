package polimi.ingsw.Main;

import polimi.ingsw.View.RMI.RMIClient;
import polimi.ingsw.View.RMI.RMIServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RMIClient client = new RMIClient();
        RMIServer server = RMIServer.bind();

        client.connect();

        client.createGame("pino");

    }
}
