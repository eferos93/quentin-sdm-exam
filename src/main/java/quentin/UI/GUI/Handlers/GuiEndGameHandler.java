package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.EndGameEvent;

public class GuiEndGameHandler implements EventHandler<EndGameEvent> {
    @Override
    public void handle(EndGameEvent event) {
        event.replay();
        event.consume();
    }
}
