package polimi.ingsw.View.userView.text;

import org.w3c.dom.Text;
import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.Chat.MessagePrivate;
import polimi.ingsw.Model.Player;

import java.io.IOException;

public class inputParser extends Thread {
    private BufferData bufferInput;
    private BufferData dataToProcess;
    private TextUI gui;
    private Player p;
    private Integer gameId;

    public inputParser(BufferData bufferInput, TextUI gui) {
        this.bufferInput = bufferInput;
        dataToProcess = new BufferData();
        this.gui = gui;
        this.p = null;
        this.gameId = null;
        this.start();
    }

    public void run() {
        String txt;
        while (!this.isInterrupted()) {

            //I keep popping data from the buffer sync
            //(so I wait myself if no data is available on the buffer)
            try {
                txt = bufferInput.popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //I popped an input from the buffer
            if (p!=null && txt.startsWith("/cs")) {
                txt = txt.charAt(3) == ' ' ? txt.substring(5) : txt.substring(4);
                String receiver = txt.substring(0, txt.indexOf(" "));
                String msg = txt.substring(receiver.length() + 1);
                gui.sendMessage(new MessagePrivate(msg, p, receiver));

            } else if (p!=null && txt.startsWith("/c")) {
                //I send a message
                txt = txt.charAt(2) == ' ' ? txt.substring(3) : txt.substring(2);
                gui.sendMessage(new Message(txt, p));

            } else if (txt.startsWith("/quit")) {
                try {
                    assert p != null;
                    gui.leave(p.getNickname(), gameId);
                    gui.youleft();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                //I didn't pop a message

                //I add the data to the buffer processed via TextUI
                dataToProcess.addData(txt);
            }
        }
    }


    public void setIdGame(Integer gameId){
        this.gameId=gameId;
    }
    public void setPlayer(Player p){
        this.p=p;
    }
    public BufferData getDataToProcess() {
        return dataToProcess;
    }



}
