package polimi.ingsw.view.userView.utilities;

import java.util.ArrayDeque;
import java.util.Queue;

public class BufferData {
    private Queue<String> data;

    public BufferData(){
        data = new ArrayDeque<String>();
    }

    public void addData(String txt){
        synchronized (this) {
            data.add(txt);
            this.notifyAll();
        }
    }

    public String popData() throws InterruptedException {
        synchronized (this){
            while(data.isEmpty()){this.wait();}
            return data.poll();
        }
    }

    public void popAllData(){
        synchronized (this) {
            while (!data.isEmpty()) {
                data.poll();
            }
        }
    }




}
