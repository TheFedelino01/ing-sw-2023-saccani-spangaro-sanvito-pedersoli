package polimi.ingsw.networking;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.networking.rmi.TaskOnNetworkDisconnection;
import polimi.ingsw.view.flow.CommonClientActions;
import polimi.ingsw.view.flow.Flow;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class HeartbeatSender extends Thread {

    private Flow flow;
    private CommonClientActions server;

    public HeartbeatSender(Flow flow, CommonClientActions server) {
        this.flow=flow;
        this.server=server;
    }


    @Override
    public void run() {
        //For the heartbeat
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
            timer.schedule(task, DefaultValue.timeoutConnection_millis);

            //send heartbeat so the server knows I am still online
            try {
                server.heartbeat();
            } catch (RemoteException e) {
                System.out.println("Connection to server lost! Impossible to send heartbeat...");
            }

            timer.cancel();

            try {
                Thread.sleep(DefaultValue.secondToWaitToSend_heartbeat);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
