package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class PrintBoard {
    private static int i, j;

    private static final Map<Stone, String> CellValue = new HashMap<>() {
        {
        put(Stone.BLACK, "[B]");
        put(Stone.WHITE, "[W]");
        put(Stone.NONE, "[ ]");
        }
    };
    public static void getCellValue(Stone stone) {System.out.print( CellValue.get(stone));}

    private static void PrintColumn(Board board, int column) {
        IntStream.range(1, board.BOARD_SIZE).forEach(x ->getCellValue(board.intersectionAt(new Position(x, column)).getStone()));
        System.out.print("\n");
    }

    public static void PrintColumn(Board board) {
        IntStream.iterate(board.BOARD_SIZE , x -> --x).limit(board.BOARD_SIZE).forEach(y -> PrintColumn(board, y));
    }
}


