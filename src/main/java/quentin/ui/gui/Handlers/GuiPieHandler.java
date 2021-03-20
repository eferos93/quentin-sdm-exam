package quentin.ui.gui.Handlers;

import javafx.event.EventHandler;
import quentin.ui.gui.Events.PieRuleEvent;

public class GuiPieHandler implements EventHandler<PieRuleEvent> {
    @Override
    public void handle(PieRuleEvent event) {
        event.switchColorPlayerLabel();
        event.consume();
    }
}
