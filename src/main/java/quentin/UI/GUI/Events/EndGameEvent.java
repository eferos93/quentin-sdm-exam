package quentin.UI.GUI.Events;

import javafx.event.Event;
import javafx.event.EventType;
import quentin.UI.GUI.GUI;

public class EndGameEvent extends Event {
    public static final EventType<EndGameEvent> END_GAME_EVENT_TYPE = new EventType<>(Event.ANY, "END_GAME");
    private GUI gui;
    EndGameEvent(GUI gui) {
        super(END_GAME_EVENT_TYPE);
        this.gui = gui;
    }

    public void replay() {
        this.gui.endUI();
    }
}
