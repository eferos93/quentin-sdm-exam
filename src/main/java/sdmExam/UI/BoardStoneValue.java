package sdmExam.UI;

import sdmExam.Stone;

import java.util.HashMap;
import java.util.Map;

public class BoardStoneValue {
        private static final Map<Stone, String> StoneValue = new HashMap<>() {
            {
                put(Stone.BLACK, "[B]");
                put(Stone.WHITE, "[W]");
                put(Stone.NONE, "[ ]");
            }
        };

        private BoardStoneValue(){};

        public static String getStoneValue(Stone stone) {
            return StoneValue.get(stone);
        }


}
