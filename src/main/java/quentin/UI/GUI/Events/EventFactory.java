package quentin.UI.GUI.Events;

import javafx.event.Event;
import java.util.stream.Stream;

public class EventFactory {

    private EventFactory() {}

    public static Stream<? extends Event> create() {
        return Stream.of(new EndGameEvent(), new PieRuleEvent(), new PassEvent());
    }

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
