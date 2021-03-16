package quentin.UI.GUI.Events;


import javafx.event.Event;
import javafx.event.EventType;
import quentin.UI.GUI.GUIBoardDisplayer;

public class PieRuleEvent extends Event {
    public static final EventType<PieRuleEvent> PIE_RULE_EVENT_TYPE = new EventType<>(Event.ANY, "PIE RULE");
    private final GUIBoardDisplayer guiBoardDisplayer;
    PieRuleEvent(GUIBoardDisplayer guiBoardDisplayer) {
        super(PIE_RULE_EVENT_TYPE);
        this.guiBoardDisplayer = guiBoardDisplayer;
    }

    public void switchColorPlayerLabel() {
        guiBoardDisplayer.switchColorPlayerLabel();
    }
}
