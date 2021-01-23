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
        IntStream.range(1, board.getBoardSize()+1).forEach(x ->displayStone(board.intersectionAt(new Position(x, column)).getStone()));
        System.out.print("W");
        System.out.print("\n");
    }

    //TODO column idexes have to be fixed
    private static String padLeft(String s) { return String.format("%" + 3 + "s", s); }

    public static void Print(Board board) {
        String BlackEdge = "  B";
        System.out.println("    "+BlackEdge.repeat(board.getBoardSize()));
        IntStream.rangeClosed(1,board.getBoardSize()).forEach(y -> PrintRow(board,y));
        System.out.println("    "+BlackEdge.repeat(board.getBoardSize()));
        System.out.print("\t");
        IntStream.range(1, board.getBoardSize()+1).forEachOrdered(index -> System.out.print(padLeft(index + " ")));
        System.out.print("\n");
    }

}


