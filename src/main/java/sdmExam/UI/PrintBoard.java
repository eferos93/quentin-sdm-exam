package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.stream.IntStream;

public class PrintBoard {

    private PrintBoard(){}

    public static void displayStone(Stone stone){
        System.out.print(BoardStoneValue.getStoneValue(stone));
    }

    //TODO i don't know why but here if i'm trying to start the range from zero it gives error please help
    private static void PrintRow(Board board, int column) {
        System.out.print(column+"\t");
        IntStream.range(1, board.getBoardSize()+1).forEachOrdered(x ->displayStone(board.intersectionAt(new Position(x, column)).getStone()));
        System.out.print("\n");
    }

    private static String padLeft(String s) { return String.format("%" + 3 + "s", s); }

    public static void Print(Board board) {
        IntStream.iterate(board.getBoardSize(), x -> --x).limit(board.getBoardSize()).forEach(y -> PrintRow(board, y));
        System.out.print("\t");
        IntStream.range(0, board.getBoardSize()).forEachOrdered(index -> System.out.print(padLeft(index + " ")));
        System.out.print("\n");
    }

}


