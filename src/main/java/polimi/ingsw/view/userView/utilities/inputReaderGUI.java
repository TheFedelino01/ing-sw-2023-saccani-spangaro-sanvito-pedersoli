package polimi.ingsw.view.userView.utilities;

public class inputReaderGUI implements InputReader {

    private final BufferData buffer;

    /**
     * Init
     */
    public inputReaderGUI(){
        buffer = new BufferData();
    }

    /**
     *
     * @return the buffer
     */
    public BufferData getBuffer(){
        return buffer;
    }

    /**
     *
     * @param txt text to add to the buffer
     */
    public synchronized void addTxt(String txt){
        buffer.addData(txt);
    }

}
