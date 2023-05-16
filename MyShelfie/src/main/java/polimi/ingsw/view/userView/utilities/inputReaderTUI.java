package polimi.ingsw.view.userView.utilities;

import polimi.ingsw.model.DefaultValue;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class inputReaderTUI extends Thread implements InputReader {
    private BufferData buffer = new BufferData();
    public inputReaderTUI(){
        this.start();
    }

    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the buffer synch
            String temp = sc.nextLine();
            buffer.addData(temp);
            System.out.println(ansi().cursorUpLine().a(" ".repeat(temp.length())));
            System.out.println(ansi().cursor(DefaultValue.row_input+1, 0));
        }
    }

    public BufferData getBuffer(){
        return buffer;
    }
}
