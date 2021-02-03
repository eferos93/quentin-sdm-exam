package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.EndGameEvent;
import quentin.UI.GUI.GUI;

public class GuiEndGameHandler implements EventHandler<EndGameEvent> {
    private final GUI gui;

    public GuiEndGameHandler(GUI gui) { this.gui = gui; }

    @Override
    public void handle(EndGameEvent event) {
        if (Boolean.TRUE.equals(gui.getGame().checkAndPerformEndGameRule())) {
            gui.outputHandler.notifyWinner(gui.getGame().getCurrentPlayer());
            gui.stop();
        }
        event.consume();
    }
}
