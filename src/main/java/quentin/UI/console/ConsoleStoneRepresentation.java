package quentin.UI.console;

import quentin.core.Stone;

import java.util.EnumMap;

public class ConsoleStoneRepresentation {
    private static final EnumMap<Stone, String> StoneValue = new EnumMap<>(Stone.class);

    private ConsoleStoneRepresentation(){
        StoneValue.put(Stone.BLACK, "[B]");
        StoneValue.put(Stone.WHITE, "[W]");
        StoneValue.put(Stone.NONE, "[ ]");
    }

    public static String getStoneValue(Stone stone) {
        return StoneValue.get(stone);
    }
}
