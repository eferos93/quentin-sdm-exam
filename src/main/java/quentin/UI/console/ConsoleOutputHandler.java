package quentin.UI.console;

import quentin.core.Board;
import quentin.core.Player;
import quentin.core.Position;
import quentin.core.Stone;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements quentin.UI.OutputHandler {

    public static void displayTitle() { System.out.println(Message.TITLE);}

    public static void displayInstructions() { System.out.println(Message.INSTRUCTIONS);}

    public static void askBoardSize() { System.out.println(Message.ASK_SIZE); }

    public static void askBlackPlayerName() {
        System.out.println(Message.ASK_BLACK_PLAYER_NAME);
    }

    public static void askWhitePlayerName() {
        System.out.println(Message.ASK_WHITE_PLAYER_NAME);
    }

    @Override
    public void notifyException(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

    public void askRowCoordinate() { System.out.println(Message.CHOOSE_ROW); }

    public void askColumnCoordinate() { System.out.println(Message.CHOOSE_COLUMN); }

    public void displayPlayer(Player player) {
        System.out.printf(Message.CURRENT_PLAYER,
                player.getName(),
                ConsoleStoneRepresentation.getStoneValue(player.getColor())
        );
    }

    @Override
    public void notifyPass(Player currentPlayer) { System.out.printf(Message.PASS_TURN, currentPlayer.getName()); }

    @Override
    public void notifyPieRule(List<Player> players) {
        System.out.printf(Message.PIE, players.get(0).getName(), ConsoleStoneRepresentation.getStoneValue(players.get(0).getColor()),
                players.get(1).getName(), ConsoleStoneRepresentation.getStoneValue(players.get(1).getColor()));
    }

    @Override
    public void notifyWinner(Player player) { System.out.printf(Message.END_GAME, player.getName(), ConsoleStoneRepresentation.getStoneValue(player.getColor())); }

    @Override
    public void askPie(String whitePlayerName) { System.out.printf(Message.QUERY_PIE, whitePlayerName); }

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
}