package sdmExam.UI;

import sdmExam.Stone;

import java.util.HashMap;
import java.util.Map;

public class BoardCellValue {
    private static final Map<Stone, String> CellValue = new HashMap<>() {
        {
            put(Stone.BLACK, "[B]");
            put(Stone.WHITE, "[W]");
            put(Stone.NONE, "[ ]");
        }
    };

    private BoardCellValue();

    public static String getCellValue(Stone colour) {
        return CellValue.get(colour);
    }

}
