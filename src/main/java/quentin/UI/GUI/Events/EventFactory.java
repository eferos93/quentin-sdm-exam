package quentin.UI.GUI.Events;

import javafx.event.Event;
import quentin.GUIQuentin;
import quentin.UI.GUI.GUI;
import quentin.UI.GUI.GUIBoardDisplayer;

public class EventFactory {

    private EventFactory() {}

    public static Event createPieRuleEvent(GUIBoardDisplayer guiBoardDisplayer) {
        return new PieRuleEvent(guiBoardDisplayer);
    }

    public static Event createPassEvent(GUIQuentin guiQuentin) {
        return new PassEvent(guiQuentin);
    }

    public static Event createEndGameEvent(GUI gui) {
        return new EndGameEvent(gui);
    }
}
