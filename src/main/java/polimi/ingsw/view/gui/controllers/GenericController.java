package polimi.ingsw.view.gui.controllers;

import polimi.ingsw.view.flow.utilities.inputReaderGUI;

public abstract class GenericController {
    private inputReaderGUI inputReaderGUI;

    public inputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }

    public void setInputReaderGUI(polimi.ingsw.view.flow.utilities.inputReaderGUI inputReaderGUI) {
        this.inputReaderGUI = inputReaderGUI;
    }
}
