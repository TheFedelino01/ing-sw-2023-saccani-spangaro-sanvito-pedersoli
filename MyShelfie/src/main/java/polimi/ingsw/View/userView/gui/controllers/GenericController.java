package polimi.ingsw.View.userView.gui.controllers;

import polimi.ingsw.View.userView.utilities.*;

public abstract class GenericController {
    private inputReaderGUI inputReaderGUI;

    public inputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }

    public void setInputReaderGUI(polimi.ingsw.View.userView.utilities.inputReaderGUI inputReaderGUI) {
        this.inputReaderGUI = inputReaderGUI;
    }
}
