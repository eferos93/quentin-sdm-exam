package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.EndGameEvent;
import quentin.UI.GUI.GUI;

public class GuiEndGameHandler implements EventHandler<EndGameEvent> {
    @Override
    public void handle(EndGameEvent event) {
        event.replay();
        event.consume();
    }
}
