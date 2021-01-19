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

    private static void PrintRow(Board board, int row) {
        System.out.print(row+"\t");
        IntStream.range(1, board.getBoardSize()+1).forEachOrdered(x ->displayStone(board.intersectionAt(new Position(x, row)).getStone()));
        System.out.print("\n");
    }

    private static String padLeft(String s) { return String.format("%" + 3 + "s", s); }

    public static void Print(Board board) {
        IntStream.iterate(board.getBoardSize() , x -> --x).limit(board.getBoardSize()).forEach(y -> PrintRow(board, y));
        System.out.print("\t");
        IntStream.range(0, board.getBoardSize()).forEachOrdered(i -> System.out.print(padLeft(i + " ")));
        System.out.print("\n");
    }

}


