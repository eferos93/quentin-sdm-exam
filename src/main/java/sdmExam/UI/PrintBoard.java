package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.stream.IntStream;

public class PrintBoard {

    private PrintBoard(){};

    public static void displayStone(Stone stone){
        System.out.print(BoardStoneValue.getStoneValue(stone));
    }

    private static void PrintRow(Board board, int column) {
        System.out.print(column+"\t");
        System.out.print("W");
        IntStream.range(1, board.getBoardSize()+1).forEachOrdered(row -> displayStone(board.intersectionAt(new Position(row, column)).getStone()));
        System.out.print("W");
        System.out.print("\n");
    }

    //TODO need to fix the column indexes somehow
    private static String padLeft(String s) { return String.format("%" + 3 + "s", s); }

    public static void Print(Board board) {
        String BlackEdge = "  B";
        System.out.println("    "+BlackEdge.repeat(board.getBoardSize()));
        IntStream.rangeClosed(1,board.getBoardSize()).limit(board.getBoardSize()).forEach(column -> PrintRow(board, column));
        System.out.println("    "+BlackEdge.repeat(board.getBoardSize()));
        System.out.print("\t");
        IntStream.range(0, board.getBoardSize()).forEachOrdered(index -> System.out.print(padLeft(index +1+" ")));
        System.out.print("\n");
    }

}


