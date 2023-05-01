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
    private int gameId;

    public inputParser(BufferData bufferInput, TextUI gui, Player p, int gameId) {
        this.bufferInput = bufferInput;
        dataToProcess = new BufferData();
        this.gui = gui;
        this.p = p;
        this.gameId = gameId;
        this.start();
    }

    public void run() {
        String txt;
        while (!this.isInterrupted()) {

            //I keep popping data from the buffer synch
            //(so I wait myself If no data is available on the buffer)
            try {
                txt = bufferInput.popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //I popped a data from the buffer
            if (txt.startsWith("/cs")) {
                txt = txt.charAt(2) == ' ' ? txt.substring(5) : txt.substring(4);
                String receiver = txt.substring(0, txt.indexOf(" "));
                String msg = txt.substring(receiver.length() + 1);
                gui.sendMessage(new MessagePrivate(msg, p, receiver));

            } else if (txt.startsWith("/c")) {
                //I send a message
                txt = txt.charAt(2) == ' ' ? txt.substring(3) : txt.substring(2);
                gui.sendMessage(new Message(txt, p));

            } else if (txt.startsWith("/quit")) {
                try {
                    gui.leave(p.getNickname(), gameId);
                    gui.youleft();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                //I didn't popped a message

                //I add the data to the buffer processed via TextUI
                dataToProcess.addData(txt);
            }
        }
    }


    public BufferData getDataToProcess() {
        return dataToProcess;
    }



}
