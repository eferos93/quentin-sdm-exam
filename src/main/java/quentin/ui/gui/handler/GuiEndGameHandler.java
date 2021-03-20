package quentin.ui.gui.handler;

import javafx.event.EventHandler;
import quentin.ui.gui.events.EndGameEvent;

public class GuiEndGameHandler implements EventHandler<EndGameEvent> {
    @Override
    public void handle(EndGameEvent event) {
        event.replay();
        event.consume();
    }
}
