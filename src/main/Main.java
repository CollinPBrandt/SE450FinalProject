package main;

import controller.interfaces.IJPaintController;
import controller.JPaintController;
import model.persistence.lists.ShapeListManager;
import model.persistence.ApplicationState;
import view.gui.PaintCanvas;
import view.gui.Gui;
import view.gui.GuiWindow;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;

public class Main {
    public static void main(String[] args){
        IGuiWindow guiWindow = new GuiWindow(new PaintCanvas());
        IUiModule uiModule = new Gui(guiWindow);
        ApplicationState appState = new ApplicationState(uiModule, new ShapeListManager());
        IJPaintController controller = new JPaintController(uiModule, appState);
        controller.setup();

        guiWindow.addMouseHandler(appState);
    }
}
