package quentin.ui.gui.Handlers;

import javafx.event.EventHandler;
import quentin.ui.gui.Events.EndGameEvent;

public class GuiEndGameHandler implements EventHandler<EndGameEvent> {
    @Override
    public void handle(EndGameEvent event) {
        event.replay();
        event.consume();
    }
}
