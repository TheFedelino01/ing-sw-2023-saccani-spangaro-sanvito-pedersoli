package polimi.ingsw.view.userView.utilities;

import static org.fusesource.jansi.Ansi.ansi;

public class inputReaderGUI implements InputReader {

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
