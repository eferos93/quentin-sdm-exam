package quentin.UI.GUI.Events;

import javafx.event.Event;

public class EventFactory {

    private EventFactory() {}

    public static Event createPieRuleEvent() {
        return new PieRuleEvent();
    }

    public static Event createPassEvent() {
        return new PassEvent();
    }

    public static Event createEndGameEvent() {
        return new EndGameEvent();
    }
}
