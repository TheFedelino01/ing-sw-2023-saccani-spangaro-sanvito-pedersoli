package polimi.ingsw.networking.rmi;

import polimi.ingsw.view.flow.Flow;

import java.util.TimerTask;

public class TaskOnNetworkDisconnection extends TimerTask {
    private Flow flow;
    public TaskOnNetworkDisconnection(Flow flow){
        this.flow=flow;
    }
    public void run() {
        flow.noConnectionError();
    }
}