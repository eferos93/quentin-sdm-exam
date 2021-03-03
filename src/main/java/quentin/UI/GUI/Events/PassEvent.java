package quentin.UI.GUI.Events;

import javafx.event.Event;
import javafx.event.EventType;


public class PassEvent extends Event {
    public static final EventType<PassEvent> PASS_EVENT_TYPE = new EventType<>(Event.ANY, "PASS");

    public PassEvent() { super(PASS_EVENT_TYPE); }

}
