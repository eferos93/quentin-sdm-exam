package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.PieRuleEvent;
import quentin.UI.GUI.GUI;

public class GuiPieHandler implements EventHandler<PieRuleEvent> {
    @Override
    public void handle(PieRuleEvent event) {
        event.switchColorPlayerLabel();
        event.consume();
    }
}
