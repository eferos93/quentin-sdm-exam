package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

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

    private static void PrintRow(Board board, int row) {
        System.out.print(row+"\t");
        IntStream.range(1, board.BOARD_SIZE+1).forEach(x ->getCellValue(board.intersectionAt(new Position(x, row)).getStone()));
        System.out.print("\n");
    }

    private static String padLeft(String s) { return String.format("%" + 3 + "s", s); }

    public static void Print(Board board) {
        IntStream.iterate(3*board.BOARD_SIZE, x -> x).limit(board.BOARD_SIZE).forEach(y -> System.out.print("-"));
        System.out.println("\n");
        IntStream.iterate(board.BOARD_SIZE , x -> --x).limit(board.BOARD_SIZE).forEach(y -> PrintRow(board, y));
        System.out.print("\t");
        IntStream.range(0, board.BOARD_SIZE).forEach(i -> System.out.print(padLeft(i + " ")));
        System.out.print("\n");
    }

}


