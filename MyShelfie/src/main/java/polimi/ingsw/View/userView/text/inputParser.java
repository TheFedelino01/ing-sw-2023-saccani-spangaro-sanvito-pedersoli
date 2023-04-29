package polimi.ingsw.View.userView.text;

import org.w3c.dom.Text;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Player;

public class inputParser extends Thread{
    private BufferData bufferInput;
    private BufferData dataToProcess;
    private TextUI gui;
    private Player p;

    public inputParser(BufferData bufferInput, TextUI gui, Player p){
        this.bufferInput=bufferInput;
        dataToProcess = new BufferData();
        this.gui=gui;
        this.p=p;
        this.start();
    }

    public void run(){
        String txt;
        while(!this.isInterrupted()){

            //I keep popping data from the buffer synch
            //(so I wait myself If no data is available on the buffer)
            try {
                txt = bufferInput.popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //I popped a data from the buffer
            if(txt.startsWith("/c")){
                //I send a message

                if (txt.startsWith("/c")) {
                    txt = txt.charAt(2) == ' '?txt.substring(3):txt.substring(2);
                    gui.sendMessage(new Message(txt, p));
                }
            }else{
                //I didn't popped a message

                //I add the data to the buffer processed via TextUI
                dataToProcess.addData(txt);
            }

        }
    }

    public BufferData getDataToProcess(){
        return dataToProcess;
    }

}
