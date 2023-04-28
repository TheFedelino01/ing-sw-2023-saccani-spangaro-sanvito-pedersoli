package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.DefaultValue;

import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.fusesource.jansi.Ansi.ansi;

public class ReadInput extends Thread{
    private BlockingDeque<String> reads;

    public ReadInput(){
        reads = null;
    }

    public synchronized BlockingDeque<String> getReads(){
        return reads;
    }

    public synchronized void setReads(BlockingDeque<String> reads){
        this.reads = reads;
    }

    public void run() {
        while (!this.isInterrupted()) {
            System.out.println(ansi().cursor(DefaultValue.row_input, 0));
            try {
                reads.put(new Scanner(System.in).nextLine());
                notifyAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
