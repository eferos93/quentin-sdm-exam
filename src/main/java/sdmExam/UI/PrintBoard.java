package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.stream.IntStream;

public class PrintBoard {

    private PrintBoard(){}

    private static void displayStone(Stone stone){
        System.out.print(ConsoleStoneRepresentation.getStoneValue(stone));
    }

    private static void printRow(Board board, int rowIndex) {
        System.out.print(rowIndex + "\t");
        System.out.print("W");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEach(columnIndex -> displayStone(board.intersectionAt(Position.in(rowIndex, columnIndex)).getStone()));
        System.out.print("W" + System.lineSeparator());
    }

    public static void printBoard(Board board) {
        System.out.println("    " + "  B".repeat(board.getBoardSize()));
        IntStream.rangeClosed(1, board.getBoardSize()).forEach(rowIndex -> printRow(board, rowIndex));
        System.out.print("    " + "  B".repeat(board.getBoardSize()) + System.lineSeparator() + "\t ");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEachOrdered(rowIndex -> System.out.printf("%" + 3 + "s", rowIndex + " "));
        System.out.println();
    }
}


