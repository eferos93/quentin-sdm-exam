package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.PassEvent;
import quentin.UI.GUI.GUI;

public class GuiPassHandler implements EventHandler<PassEvent> {

    private final GUI gui;

    public GuiPassHandler(GUI gui) { this.gui = gui; }

    @Override
    public void handle(PassEvent event) {
        gui.getGame().passTurn(gui.getGame().getCurrentPlayer());
        event.consume();
    }
}
