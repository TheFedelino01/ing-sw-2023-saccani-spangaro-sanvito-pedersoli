package polimi.ingsw.View.userView.text;

import java.util.Scanner;

public class inputReader extends Thread{
    private BufferData buffer = new BufferData();
    public inputReader(){
        this.start();
    }

    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the buffer synch
            buffer.addData(sc.nextLine());
        }
    }

    public BufferData getBuffer(){
        return buffer;
    }
}
