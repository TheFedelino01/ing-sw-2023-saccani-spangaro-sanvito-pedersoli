package polimi.ingsw.View.userView.text;

import polimi.ingsw.Model.Chat.Message;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.GameCaseType;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;

import java.util.InputMismatchException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.fusesource.jansi.Ansi.ansi;

public class SaveReads extends Thread {
    private GameModelImmutable gameModel;
    private String msg, readString, nickname;
    private Integer readInt;
    private Console console;
    private Message message;
    private GameCaseType chosen;
    private BlockingDeque<String> reads;
    private TextUI ui;
    private ReadInput readInput;

    public SaveReads(TextUI ui) {
        gameModel = null;
        readInput = new ReadInput();
        msg = null;
        readInt = null;
        readString = null;
        message = null;
        console = null;
        nickname = null;
        chosen = GameCaseType.none;
        this.ui = ui;
    }

    public SaveReads(GameModelImmutable gameModel, String nickname, Console console, TextUI ui) {
        this.gameModel = gameModel;
        this.msg = null;
        readInput = new ReadInput();
        readInt = null;
        readString = null;
        message = null;
        reads = new LinkedBlockingDeque<>();
        this.console = console;
        this.nickname = nickname;
        chosen = GameCaseType.none;
        this.ui = ui;
    }


    public void run() {
        readInput.setReads(reads);
        readInput.start();
        while (!this.isInterrupted()) {
            setReads(readInput.getReads());
            String temp;
            int tempInt;
            try {
                console.alwaysShow(gameModel, nickname);
                while(msg == null){
                    wait();
                }
                System.out.println(ansi().cursor(DefaultValue.row_input, 0).a(msg).a(" ".repeat(console.getLengthLongestMessage())));
                System.out.flush();
                temp = reads.take();
                if (temp.equals(""))
                    continue;
                if (temp.startsWith("/c")) {
                    if (temp.charAt(2) == ' ') {
                        ui.sendMessage(new Message(temp.substring(3), gameModel.getPlayerEntity(nickname)));
                    } else {
                        ui.sendMessage(new Message(temp.substring(2), gameModel.getPlayerEntity(nickname)));
                    }
                    System.out.println(ansi().cursor(DefaultValue.row_input, 0).a(msg).a(" ".repeat(console.getLengthLongestMessage())));
                } else {
                    switch (chosen) {
                        case numTilesToPick -> {
                            try {
                                tempInt = Integer.parseInt(temp);
                                if (tempInt < DefaultValue.minNumOfGrabbableTiles || tempInt > DefaultValue.maxNumOfGrabbableTiles) {
                                    readInt = null;
                                    System.out.println("Nan");
                                } else {
                                    readInt = tempInt;
                                }
                            } catch (InputMismatchException | NumberFormatException e) {
                                System.out.println("Nan");
                            }

                        }
                        case rowTilesToPick, colTilesToPick -> {
                            try {
                                tempInt = Integer.parseInt(temp);
                                if (tempInt >= DefaultValue.PlaygroundSize) {
                                    readInt = null;
                                    System.out.println("Nan");
                                } else {
                                    readInt = tempInt;
                                }
                            } catch (InputMismatchException | NumberFormatException e) {
                                System.out.println("Nan");
                            }

                        }
                        case colPlaceTile -> {
                            try {
                                tempInt = Integer.parseInt(temp);
                                if (tempInt >= DefaultValue.NumOfColumnsShelf) {
                                    readInt = null;
                                    System.out.println("Nan");
                                } else {
                                    readInt = tempInt;
                                }
                            } catch (InputMismatchException | NumberFormatException e) {
                                System.out.println("Nan");
                            }
                        }
                        case indexPlaceTile -> {
                            try {
                                tempInt = Integer.parseInt(temp);
                                if (tempInt >= gameModel.getPlayerEntity(gameModel.getNicknameCurrentPlaying()).getInHandTile().size()) {
                                    readInt = null;
                                    System.out.println("Nan");
                                } else {
                                    readInt = tempInt;
                                }
                            } catch (InputMismatchException | NumberFormatException e) {
                                System.out.println("Nan");
                            }
                        }
                        case dirTilesToPick -> {
                            if (temp.equals("r") || temp.equals("d") || temp.equals("l") || temp.equals("u")) {
                                readString = temp;
                            } else {
                                readString = null;
                                System.out.println("Nan");
                            }
                        }
                    }

                }
            } catch (InterruptedException e) {
                console.clearCMD();
                System.out.println("Input interrupted error" + e.getMessage());
            }

        }
    }

    public synchronized void setReads(BlockingDeque<String> reads) {
        this.reads = reads;
    }

    public synchronized void setChosen(GameCaseType chosen) {
        this.chosen = chosen;
    }

    public synchronized void setMsg(String msg) {
        this.msg = msg;
    }

    public synchronized Integer getReadInt() {
        while (readInt == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return readInt;
    }

    public synchronized String getReadString() {
        while (readString == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return readString;
    }

}
