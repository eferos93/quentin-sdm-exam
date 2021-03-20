package quentin.ui.gui.handler;

import javafx.event.EventHandler;
import quentin.ui.gui.events.PieRuleEvent;

public class GuiPieHandler implements EventHandler<PieRuleEvent> {
    @Override
    public void handle(PieRuleEvent event) {
        event.switchColorPlayerLabel();
        event.consume();
    }
}
