package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.HashMap;
import java.util.Map;

public class PrintBoard {
    private static int i, j;

    private static final Map<Stone, String> CellValue = new HashMap<>() {
        {
        put(Stone.BLACK, "[B]");
        put(Stone.WHITE, "[W]");
        put(Stone.NONE, "[ ]");
        }
    };
    public static String getCellValue(Stone stone) {
        return CellValue.get(stone);
    }


    public static void Print(Board board) {
        for (i = 0; i < 13; i++) {
            System.out.print("\n");
            for (j = 0; j < 13; j++) {
                System.out.print("[]");
            }

        }

    }

}
