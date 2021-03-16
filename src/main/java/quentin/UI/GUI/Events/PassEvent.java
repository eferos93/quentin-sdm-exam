package quentin.UI.GUI.Events;

import javafx.event.Event;
import javafx.event.EventType;
import quentin.GUIQuentin;


public class PassEvent extends Event {
    public static final EventType<PassEvent> PASS_EVENT_TYPE = new EventType<>(Event.ANY, "PASS");
    private GUIQuentin guiQuentin;
    PassEvent(GUIQuentin guiQuentin) {
        super(PASS_EVENT_TYPE);
        this.guiQuentin = guiQuentin;
    }

    public void passTurn() {
        this.guiQuentin.passTurn();
    }

}
