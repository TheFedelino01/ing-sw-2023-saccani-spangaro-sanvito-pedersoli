package polimi.ingsw.view.userView.utilities;

import java.util.ArrayDeque;
import java.util.Queue;

public class BufferData {
    private Queue<String> data;

    /**
     * init
     */
    public BufferData(){
        data = new ArrayDeque<>();
    }

    /**
     * Adds one element to the queue
     * @param txt element to add
     */
    public void addData(String txt){
        synchronized (this) {
            data.add(txt);
            this.notifyAll();
        }
    }

    /**
     * Pops one element from the queue
     * @return the popped element
     * @throws InterruptedException
     */
    public String popData() throws InterruptedException {
        synchronized (this){
            while(data.isEmpty()){this.wait();}
            return data.poll();
        }
    }

    /**
     *  Empties the queue
     */
    public void popAllData(){
        synchronized (this) {
            while (!data.isEmpty()) {
                data.poll();
            }
        }
    }




}
