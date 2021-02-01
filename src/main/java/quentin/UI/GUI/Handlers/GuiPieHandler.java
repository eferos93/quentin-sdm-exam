package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import quentin.UI.GUI.Events.PieRuleEvent;

public class GuiPieHandler implements EventHandler<PieRuleEvent> {

    private final GUI gui;

    public GuiPieHandler(GUI g) { gui = g; }

    @Override
    public void handle(PieRuleEvent event) {
        if (gui.getGame().checkAndPerformPieRule(gui.getGame().getCurrentPlayer())) {
            gui.getBoardFiller().switchLabelsColors(gui.getLabelBoard());
        }
        event.consume();
    }
}