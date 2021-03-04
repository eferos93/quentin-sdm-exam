package quentin.UI.GUI.Events;


import javafx.event.Event;
import javafx.event.EventType;

public class PieRuleEvent extends Event {
    public static final EventType<PieRuleEvent> PIE_RULE_EVENT_TYPE = new EventType<>(Event.ANY, "PIE RULE");

    PieRuleEvent() { super(PIE_RULE_EVENT_TYPE); }
}
