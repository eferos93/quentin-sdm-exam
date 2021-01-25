package quentin.UI;

import quentin.core.Stone;

import java.util.HashMap;
import java.util.Map;

public class ConsoleStoneRepresentation {
    private static final Map<Stone, String> StoneValue = new HashMap<>() {
        {
            put(Stone.BLACK, "[B]");
            put(Stone.WHITE, "[W]");
            put(Stone.NONE, "[ ]");
        }
    };

    private ConsoleStoneRepresentation(){}

    public static String getStoneValue(Stone stone) {
        return StoneValue.get(stone);
    }
}
