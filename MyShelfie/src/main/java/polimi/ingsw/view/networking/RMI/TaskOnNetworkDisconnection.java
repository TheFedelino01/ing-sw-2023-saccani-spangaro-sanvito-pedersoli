package polimi.ingsw.view.networking.RMI;

import polimi.ingsw.view.userView.Flow;
import polimi.ingsw.view.userView.UI;

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