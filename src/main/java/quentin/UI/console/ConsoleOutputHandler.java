package quentin.UI.console;

import quentin.core.Board;
import quentin.core.Player;
import quentin.core.Position;
import quentin.core.Stone;

import java.util.stream.IntStream;

public class ConsoleOutputHandler implements quentin.UI.OutputHandler {

    @Override
    public void notifyInvalidCoordinateInput() { System.out.println(Message.INVALID_COORDINATE_INPUT); }

    @Override
    public void notifyInvalidSizeInput() { System.out.println(Message.INVALID_SIZE_INPUT); }

    @Override
    public void displayTitle() { System.out.println(Message.TITLE);}

    @Override
    public void displayInstructions() { System.out.println(Message.INSTRUCTIONS);}

    @Override
    public void notifyBlackPlayerPlayFirst() {System.out.println(Message.BLACK_PLAYS_FIRST);}

    @Override
    public void askBoardSize() { System.out.println(Message.ASK_SIZE); }

    @Override
    public void askRowCoordinate() { System.out.println(Message.CHOOSE_ROW); }

    @Override
    public void askColumnCoordinate() { System.out.println(Message.CHOOSE_COLUMN); }

    @Override
    public void displayPlayer(Player player) {
        System.out.printf(Message.CURRENT_PLAYER,
                player.getName(),
                ConsoleStoneRepresentation.getStoneValue(player.getColor())
        );
    }

    @Override
    public void notifyPass(Player currentPlayer) { System.out.printf(Message.PASS_TURN, currentPlayer.getName()); }

    @Override
    public void notifyPieRule(Player player1, Player player2) {
        System.out.printf(Message.PIE, player1.getName(), ConsoleStoneRepresentation.getStoneValue(player1.getColor()),
                player2.getName(), ConsoleStoneRepresentation.getStoneValue(player2.getColor()));
    }

    @Override
    public void notifyWinner(Player player) { System.out.printf(Message.END_GAME, player.getName(), ConsoleStoneRepresentation.getStoneValue(player.getColor())); }

    @Override
    public void askPie() { System.out.println(Message.QUERY_PIE); }

    private void displayStone(Stone stone){
        System.out.print(ConsoleStoneRepresentation.getStoneValue(stone));
    }

    private void displayRow(Board board, int rowIndex) {
        System.out.print(rowIndex + "   ");
        System.out.print("W");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEach(columnIndex ->
                        displayStone(board.intersectionAt(Position.in(rowIndex, columnIndex)).getStone())
                );
        System.out.print("W" + System.lineSeparator());
    }

    @Override
    public void displayBoard(Board board) {
        System.out.println("    " + "  B".repeat(board.getBoardSize()));
        IntStream.rangeClosed(1, board.getBoardSize()).forEach(rowIndex -> displayRow(board, rowIndex));
        System.out.print("    " + "  B".repeat(board.getBoardSize()) + System.lineSeparator() + "    ");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEachOrdered(columnIndex -> System.out.print("  " + columnIndex));
        System.out.println();
    }

    @Override
    public void askBlackPlayerName() {
        System.out.println(Message.ASK_BLACK_PLAYER_NAME);
    }

    @Override
    public void askWhitePlayerName() {
        System.out.println(Message.ASK_WHITE_PLAYER_NAME);
    }

    @Override
    public void notifyException(String message) {
        System.out.println(message);
    }
}