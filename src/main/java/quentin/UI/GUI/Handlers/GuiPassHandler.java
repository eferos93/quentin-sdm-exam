package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.GUIQuentin;
import quentin.UI.GUI.Events.PassEvent;

public class GuiPassHandler implements EventHandler<PassEvent> {
    @Override
    public void handle(PassEvent event) {
        event.passTurn();
        event.consume();
    }
}
