package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.GUIQuentin;
import quentin.UI.GUI.Events.PassEvent;

public class GuiPassHandler implements EventHandler<PassEvent> {

    private final GUIQuentin game;

    public GuiPassHandler(GUIQuentin game) { this.game = game; }

    @Override
    public void handle(PassEvent event) {
        game.passTurn();
        event.consume();
    }
}
