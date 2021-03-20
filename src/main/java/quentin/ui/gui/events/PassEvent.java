package quentin.ui.gui.events;

import javafx.event.Event;
import javafx.event.EventType;
import quentin.GUIQuentin;


public class PassEvent extends Event {
    public static final EventType<PassEvent> PASS_EVENT_TYPE = new EventType<>(Event.ANY, "PASS");
    private final GUIQuentin guiQuentin;
    PassEvent(GUIQuentin guiQuentin) {
        super(PASS_EVENT_TYPE);
        this.guiQuentin = guiQuentin;
    }

    public void passTurn() {
        this.guiQuentin.passTurn();
    }

}
