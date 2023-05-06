package polimi.ingsw.View.userView.utilities;

import polimi.ingsw.Model.DefaultValue;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class inputReaderGUI implements inputReader{

    private BufferData buffer;
    public inputReaderGUI(){
        buffer = new BufferData();
    }

    public BufferData getBuffer(){
        return buffer;
    }
    public synchronized void addTxt(String txt){
        buffer.addData(txt);
    }

}
