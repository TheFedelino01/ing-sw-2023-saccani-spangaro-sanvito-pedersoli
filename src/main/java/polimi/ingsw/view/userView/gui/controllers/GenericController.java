package polimi.ingsw.view.userView.gui.controllers;

import polimi.ingsw.view.userView.utilities.*;

public abstract class GenericController {
    private inputReaderGUI inputReaderGUI;

    public inputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }

    public void setInputReaderGUI(polimi.ingsw.view.userView.utilities.inputReaderGUI inputReaderGUI) {
        this.inputReaderGUI = inputReaderGUI;
    }
}
