package quentin.UI.console;

import quentin.core.Color;

import java.util.HashMap;
import java.util.Map;

public class IntersectionConsoleRepresentation {
    private final Map<Color, String> intersectionRepresentation = new HashMap<>() {{
        put(Color.BLACK, "[B]");
        put(Color.WHITE, "[W]");
        put(null, "[ ]");
    }};

    public String getStoneValue(Color color) {
        return  intersectionRepresentation.get(color);
    }
}
