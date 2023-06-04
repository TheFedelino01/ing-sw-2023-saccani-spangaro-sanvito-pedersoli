package polimi.ingsw.view.gui.controllers;

import polimi.ingsw.view.flow.utilities.inputReaderGUI;

/**
 * GenericController class.
 */
public abstract class GenericController {
    private inputReaderGUI inputReaderGUI;

    /**
     * Method to get the input reader GUI.
     * @return the input reader GUI
     */
    public inputReaderGUI getInputReaderGUI() {
        return inputReaderGUI;
    }

    /**
     * Method to set the input reader GUI.
     * @param inputReaderGUI the input reader GUI
     */
    public void setInputReaderGUI(polimi.ingsw.view.flow.utilities.inputReaderGUI inputReaderGUI) {
        this.inputReaderGUI = inputReaderGUI;
    }
}
