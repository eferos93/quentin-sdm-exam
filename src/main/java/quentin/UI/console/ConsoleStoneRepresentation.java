package quentin.UI.console;

import quentin.core.Color;

import java.util.EnumMap;

public class ConsoleStoneRepresentation {
    private static final EnumMap<Color, String> StoneValue = new EnumMap<>(Color.class);

    private ConsoleStoneRepresentation(){
    }

    public static String getStoneValue(Color color) {
        StoneValue.put(Color.BLACK, "[B]");
        StoneValue.put(Color.WHITE, "[W]");
        StoneValue.put(Color.NONE, "[ ]");
        return StoneValue.get(color);
    }
}
