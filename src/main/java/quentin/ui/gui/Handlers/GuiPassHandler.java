package quentin.ui.gui.Handlers;

import javafx.event.EventHandler;
import quentin.ui.gui.Events.PassEvent;

public class GuiPassHandler implements EventHandler<PassEvent> {
    @Override
    public void handle(PassEvent event) {
        event.passTurn();
        event.consume();
    }
}
