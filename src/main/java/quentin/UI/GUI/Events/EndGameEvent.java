package quentin.UI.GUI.Events;

import javafx.event.Event;
import javafx.event.EventType;

public class EndGameEvent extends Event {
    public static final EventType<EndGameEvent> END_GAME_EVENT_TYPE = new EventType<>(Event.ANY, "END_GAME");

    EndGameEvent() { super(END_GAME_EVENT_TYPE); }
}
