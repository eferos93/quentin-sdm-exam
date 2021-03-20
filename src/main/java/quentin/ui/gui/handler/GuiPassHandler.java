package quentin.ui.gui.handler;

import javafx.event.EventHandler;
import quentin.ui.gui.events.PassEvent;

public class GuiPassHandler implements EventHandler<PassEvent> {
    @Override
    public void handle(PassEvent event) {
        event.passTurn();
        event.consume();
    }
}
