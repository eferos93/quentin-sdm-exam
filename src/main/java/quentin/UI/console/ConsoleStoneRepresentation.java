package quentin.UI.console;

import quentin.core.Stone;

import java.util.EnumMap;

public class ConsoleStoneRepresentation {
    private static final EnumMap<Stone, String> StoneValue = new EnumMap<>(Stone.class);

    private ConsoleStoneRepresentation(){
    }

    public static String getStoneValue(Stone stone) {
        StoneValue.put(Stone.BLACK, "[B]");
        StoneValue.put(Stone.WHITE, "[W]");
        StoneValue.put(Stone.NONE, "[ ]");
        return StoneValue.get(stone);
    }
}
