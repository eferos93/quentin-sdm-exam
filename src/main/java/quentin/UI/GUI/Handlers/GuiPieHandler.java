package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.PieRuleEvent;
import quentin.UI.GUI.GUI;

public class GuiPieHandler implements EventHandler<PieRuleEvent> {

    private final GUI gui;

    public GuiPieHandler(GUI gui) { this.gui = gui; }

    @Override
    public void handle(PieRuleEvent event) {
        if (gui.getGame().checkAndPerformPieRule(gui.getGame().getCurrentPlayer())) {
            gui.getBoardFiller().switchColorPlayerLabel(gui.getLabelBoard());
        }
        event.consume();
    }
}
