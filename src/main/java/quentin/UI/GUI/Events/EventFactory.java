package quentin.UI.GUI.Events;

import javafx.event.Event;
import quentin.UI.GUI.GUIBoardDisplayer;

public class EventFactory {

    private EventFactory() {}

    public static Event createPieRuleEvent(GUIBoardDisplayer guiBoardDisplayer) {
        return new PieRuleEvent(guiBoardDisplayer);
    }

    public static Event createPassEvent() {
        return new PassEvent();
    }

    public static Event createEndGameEvent() {
        return new EndGameEvent();
    }
}
