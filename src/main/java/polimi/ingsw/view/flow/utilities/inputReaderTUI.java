package polimi.ingsw.view.flow.utilities;

import polimi.ingsw.model.DefaultValue;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;
import static polimi.ingsw.view.text.PrintAsync.printAsync;

/**
 * InputReaderTUI class
 * InputReaderTUI is the class that reads the input and add it to the buffer
 */
public class inputReaderTUI extends Thread implements InputReader {
    private final BufferData buffer = new BufferData();

    /**
     * Init
     */
    public inputReaderTUI(){
        this.start();
    }

    /**
     * Reads player's inputs
     */
    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the buffer synch
            String temp = sc.nextLine();
            buffer.addData(temp);
            printAsync(ansi().cursorUpLine().a(" ".repeat(temp.length())));
            printAsync(ansi().cursor(DefaultValue.row_input + 1, 0));
        }
    }

    /**
     * @return the buffer
     */
    public BufferData getBuffer(){
        return buffer;
    }
}
