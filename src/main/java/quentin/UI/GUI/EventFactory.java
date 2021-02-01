package quentin.UI.GUI;

import javafx.event.Event;
import quentin.UI.GUI.Events.EndGameEvent;
import quentin.UI.GUI.Events.PassEvent;
import quentin.UI.GUI.Events.PieRuleEvent;

import java.util.stream.Stream;

public class EventFactory {

    private EventFactory() {}

    public static Stream<? extends Event> create() {
        return Stream.of(new EndGameEvent(), new PieRuleEvent(), new PassEvent());
    }
}
