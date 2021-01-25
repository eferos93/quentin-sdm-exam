package quentin.UI;

import quentin.core.Board;
import quentin.core.Player;
import quentin.core.Position;
import quentin.core.Stone;

import java.util.stream.IntStream;

public class ConsoleOutputHandler {

    private static class Message {
        public static final String TITLE =  "QUENTIN GAME";
        public final static String INSTRUCTIONS = """
            The Black player B should connect the black board sides (top and bottom)
            The White player W should connect the white board sides (left and right)
            For more info visit: https://boardgamegeek.com/boardgame/124095/quentin""";
        public static final String BLACK_PLAYS_FIRST = "Black player plays first";
        public static final String CHOOSE_ROW = "Choose row coordinate: ";
        public static final String CHOOSE_COLUMN = "Choose column coordinate: ";
        public static final String QUERY_PIE = "Apply pie rule? yes/no";
        public static final String PIE = """
            Pie rule applied.
            %s's color: %s
            %s's color: %s 
            """;
        public static final String ASK_SIZE = "Choose the dimension of the board between 4 and 13";
        public static final String INVALID_SIZE_INPUT = "Invalid size. Please select a valid input size for the board.";
        public static final String INVALID_COORDINATE_INPUT = "Invalid input coordinate. Please choose a valid intersection inside the board";
        public static final String CURRENT_PLAYER = "%s's turn. %s";
        public static final String END_GAME = "Congratulations to %s, you won!";
        public static final String PASS_TURN = "You have to pass";
    }


    public static void notifyInvalidCoordinateInput() { System.out.println(Message.INVALID_COORDINATE_INPUT); }
    public static void notifyInvalidSizeInput() { System.out.println(Message.INVALID_SIZE_INPUT); }
    public static void printTitle() { System.out.println(Message.TITLE);}
    public static void printInstructions() { System.out.println(Message.INSTRUCTIONS);}
    public static void blackPlayerPlayFirst() {System.out.println(Message.BLACK_PLAYS_FIRST);}
    public static void askBoardSize() { System.out.println(Message.ASK_SIZE); }
    public static void askRowCoordinate() { System.out.println(Message.CHOOSE_ROW); }
    public static void askColumnCoordinate() { System.out.println(Message.CHOOSE_COLUMN); }
    public static void displayPlayer(Player player) {
        System.out.printf(Message.CURRENT_PLAYER,
                player.getName(),
                ConsoleStoneRepresentation.getStoneValue(player.getColor())
        );
    }
    public static void notifyPass(){ System.out.println(Message.PASS_TURN); }
    public static void notifyPieRule(Player player1, Player player2) {
        System.out.printf(Message.PIE, player1.getName(), ConsoleStoneRepresentation.getStoneValue(player1.getColor()),
                player2.getName(), ConsoleStoneRepresentation.getStoneValue(player2.getColor()));
    }

    public static void notifyWinner(Player player) { System.out.printf(Message.END_GAME, player.getName()); }
    public static void askPie() { System.out.println(Message.QUERY_PIE); }

    private static void displayStone(Stone stone){
        System.out.print(ConsoleStoneRepresentation.getStoneValue(stone));
    }

    private static void printRow(Board board, int rowIndex) {
        System.out.print(rowIndex + "\t");
        System.out.print("W");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEach(columnIndex ->
                        displayStone(board.intersectionAt(Position.in(rowIndex, columnIndex)).getStone())
                );
        System.out.print("W" + System.lineSeparator());
    }

    public static void printBoard(Board board) {
        System.out.println("    " + "  B".repeat(board.getBoardSize()));
        IntStream.rangeClosed(1, board.getBoardSize()).forEach(rowIndex -> printRow(board, rowIndex));
        System.out.print("    " + "  B".repeat(board.getBoardSize()) + System.lineSeparator() + "\t");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEachOrdered(columnIndex -> System.out.print("  " + columnIndex));
        System.out.println();
    }
}