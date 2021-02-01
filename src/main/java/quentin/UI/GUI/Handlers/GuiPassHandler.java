package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.PassEvent;

public class GuiPassHandler implements EventHandler<PassEvent> {

    private final GUI gui;

    public GuiPassHandler(GUI g) { gui = g; }

    @Override
    public void handle(PassEvent event) {
        gui.getGame().isPlayerAbleToMakeAMove(gui.getGame().getLastPlay().getOppositeColor());
        event.consume();
    }
}
