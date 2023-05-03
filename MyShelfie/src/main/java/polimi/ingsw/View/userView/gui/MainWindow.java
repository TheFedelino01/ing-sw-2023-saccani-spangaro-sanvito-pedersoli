package polimi.ingsw.View.userView.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow {
    //create the main window with javaFX where the player can choose if create a new game, join an existing one or load a game
    private final JFrame frame;
    private final List<JButton> buttons;
    private final JPanel buttonPanel;
    //Text
    private final JLabel text;


    public MainWindow() {
        frame = new JFrame("Main");
        buttons = new ArrayList<>();
        buttonPanel = new JPanel();
        text = new JLabel("Choose an option");
        buttonPanel.setLayout(new GridLayout(5, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void init() {

        //adds the text to the panel and center it
        text.setHorizontalAlignment(JLabel.CENTER);
        buttonPanel.add(text);
        buttons.add(new JButton("Create a new game"));
        buttons.add(new JButton("Join an existing game"));
        buttons.add(new JButton("Load a game"));
        buttons.add(new JButton("Exit"));

        for (JButton b : buttons) {
            //sets buttons dimensions
            b.setPreferredSize(new Dimension(50, 50));
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

}
