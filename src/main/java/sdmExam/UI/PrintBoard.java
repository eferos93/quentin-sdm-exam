package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.stream.IntStream;

public class PrintBoard {

    private PrintBoard(){}

    public static void displayStone(Stone stone){
        System.out.print(ConsoleStoneRepresentation.getStoneValue(stone));
    }

    public static void printRow(Board board, int rowIndex) {
        System.out.print(rowIndex + "\t");
        System.out.print("W");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEach(columnIndex -> displayStone(board.intersectionAt(Position.in(rowIndex, columnIndex)).getStone()));
        System.out.print("W" + System.lineSeparator());
    }

    //TODO column indexes have to be fixed
    private static String padLeft(String s) { return String.format("%" + 3 + "s", s); }

    public static void printBoard(Board board) {
        String BlackSide = "  B";
        System.out.println("    "+BlackSide.repeat(board.getBoardSize()));
        IntStream.rangeClosed(1,board.getBoardSize()).forEach(y -> printRow(board,y));
        System.out.println("    "+BlackSide.repeat(board.getBoardSize()));
        System.out.print("\t");
        IntStream.range(1, board.getBoardSize()+1).forEachOrdered(index -> System.out.print(padLeft(index + " ")));
        System.out.print("\n");
    }

}


