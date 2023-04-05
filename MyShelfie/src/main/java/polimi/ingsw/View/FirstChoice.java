package polimi.ingsw.View;

import polimi.ingsw.Model.DefaultValue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FirstChoice {
    private final JFrame frame;
    private final List<JButton> buttons;
    private final JPanel buttonPanel;
    private boolean check0;
    private boolean check1;
    private boolean chosenRMI;
    private boolean chosenGUI;

    public FirstChoice() {
        check0 = false;
        check1 = false;
        frame = new JFrame("My Shelfie");
        buttons = new ArrayList<>();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {
        //adds the 4 initial buttons
        buttonSetup("RMI");
        buttonSetup("Socket");
        buttonSetup("GUI");
        buttonSetup("TextUI");
        for (JButton b : buttons) {
            //sets buttons dimensions
            b.setPreferredSize(new Dimension(DefaultValue.defButtonXSize, DefaultValue.defButtonYSize));
            //adds the frame to the panel
            buttonPanel.add(b);
        }
        //adds the panel to the frame
        frame.add(buttonPanel);
        //automatically sets the frame size so that all the buttons fit
        frame.pack();
        //sets the frame location centered onscreen
        frame.setLocationRelativeTo(null);
        //sets frame as visible
        frame.setVisible(true);

        //for debugging purposes
        /*
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println(chosenRMI ? "True" : "False");
                System.out.println(chosenGUI ? "True" : "False");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        */

    }

    private void buttonSetup(String name) {
        switch (name) {
            case ("RMI"), ("Socket") -> {
                buttons.add(new JButton(name));
                buttons.get(buttons.size() - 1).setActionCommand(name);
                buttons.get(buttons.size() - 1).addActionListener(e -> {
                    chosenRMI = "RMI".equals(e.getActionCommand());
                    check0 = true;
                });
            }
            case ("GUI"), ("TextUI") -> {
                buttons.add(new JButton(name));
                buttons.get(buttons.size() - 1).setActionCommand(name);
                buttons.get(buttons.size() - 1).addActionListener(e -> {
                    chosenGUI = "GUI".equals(e.getActionCommand());
                    check1 = true;
                });
            }
        }

    }

    public void close(){
        frame.dispose();
    }

    public synchronized boolean isCheck0() {
        return check0;
    }

    public synchronized boolean isCheck1() {
        return check1;
    }

    public synchronized boolean isChosenRMI() {
        return chosenRMI;
    }

    public synchronized boolean isChosenGUI() {
        return chosenGUI;
    }
}
