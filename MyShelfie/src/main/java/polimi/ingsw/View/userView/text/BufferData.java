package polimi.ingsw.View.userView.text;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BufferData {
    private Queue<String> data;

    public BufferData(){
        data = new ArrayDeque<>();
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
