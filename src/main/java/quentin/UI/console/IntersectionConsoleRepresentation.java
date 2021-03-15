package quentin.UI.console;

import quentin.core.Colour;

import java.util.HashMap;
import java.util.Map;

public class IntersectionConsoleRepresentation {
    private final Map<Colour, String> intersectionRepresentation = new HashMap<>() {{
        put(Colour.BLACK, "[B]");
        put(Colour.WHITE, "[W]");
        put(null, "[ ]");
    }};

    public String getStoneValue(Colour colour) {
        return  intersectionRepresentation.get(colour);
    }
}
