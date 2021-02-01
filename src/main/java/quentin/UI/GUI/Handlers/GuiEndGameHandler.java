package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.EndGameEvent;

public class GuiEndGameHandler implements EventHandler<EndGameEvent> {
    private final GUI gui;

    public GuiEndGameHandler(GUI g) { gui = g; }

    @Override
    public void handle(EndGameEvent event) {
        if (gui.getGame().checkAndPerformEndGameRule()) {
            gui.stop();
        }
        event.consume();
    }
//TODO it will be necessary to implement a GUI class for this handler and for the following
}
