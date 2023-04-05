package polimi.ingsw.Main;

import polimi.ingsw.View.FirstChoice;

public class Main {
    private static boolean hasChosen, isRMI, isGUI;
    private static boolean testing = false;
    public static void main(String[] args) {
        firstPanel();
    }

    public static synchronized void firstPanel(){
        hasChosen = false;
        FirstChoice panel = new FirstChoice();
        panel.init();
        Thread check = new Thread(
                () -> {
                    while (!hasChosen) {
                        if (panel.isCheck1() && panel.isCheck0()){
                            hasChosen = true;
                        }
                    }
                    isRMI = panel.isChosenRMI();
                    isGUI = panel.isChosenGUI();
                    panel.close();
                    testing = true;
                }
        );
        check.setDaemon(true);
        check.start();
        while(!testing){
            //stops elaboration
        }
        System.out.println(isRMI ? "True" : "False");
        System.out.println(isGUI ? "True" : "False");
    }
}
