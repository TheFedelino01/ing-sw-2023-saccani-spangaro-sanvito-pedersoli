package polimi.ingsw.View;

import polimi.ingsw.Model.DefaultValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class FirstChoice {
    private final JFrame frame;
    private final List<JButton> buttons;
    private final JPanel buttonPanel;

    private boolean chosenRMI;
    private boolean chosenGUI;
    public FirstChoice() {
        frame = new JFrame("My Shelfie");
        buttons = new ArrayList<>();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {
        //adds the 4 initial buttons
        buttons.add(new JButton("RMI"));
        buttons.get(buttons.size()-1).setActionCommand("RMI");
        buttons.get(buttons.size()-1).addActionListener(this::actionPerformedConnection);
        buttons.add(new JButton("Socket"));
        buttons.get(buttons.size()-1).setActionCommand("Socket");
        buttons.get(buttons.size()-1).addActionListener(this::actionPerformedConnection);
        buttons.add(new JButton("GUI"));
        buttons.get(buttons.size()-1).setActionCommand("GUI");
        buttons.get(buttons.size()-1).addActionListener(this::actionPerformedVisuals);
        buttons.add(new JButton("TextUI"));
        buttons.get(buttons.size()-1).setActionCommand("TextUI");
        buttons.get(buttons.size()-1).addActionListener(this::actionPerformedVisuals);
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
    }

    public void actionPerformedConnection(ActionEvent e){
        chosenRMI="RMI".equals(e.getActionCommand());
        System.out.println(chosenRMI?"True":"False");
    }

    public void actionPerformedVisuals(ActionEvent e){
        chosenGUI="GUI".equals(e.getActionCommand());
        System.out.println(chosenGUI?"True":"False");
    }

    public boolean isChosenRMI() {
        return chosenRMI;
    }

    public boolean isChosenGUI() {
        return chosenGUI;
    }
}
