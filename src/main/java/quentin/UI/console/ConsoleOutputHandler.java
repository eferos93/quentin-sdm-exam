package quentin.UI.console;

import quentin.core.Board;
import quentin.core.Player;
import quentin.core.Position;
import quentin.core.Color;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements quentin.UI.OutputHandler {

    private final IntersectionConsoleRepresentation intersectionConsoleRepresentation = new IntersectionConsoleRepresentation();

    public void displayTitle() { System.out.println(Message.TITLE);}

    public void displayInstructions() { System.out.println(Message.INSTRUCTIONS);}

    public void askBoardSize() { System.out.println(Message.ASK_SIZE); }

    public void askBlackPlayerName() {
        System.out.println(Message.ASK_BLACK_PLAYER_NAME);
    }

    public void askWhitePlayerName() {
        System.out.println(Message.ASK_WHITE_PLAYER_NAME);
    }

    public void printWantToReplay() {
        System.out.println(System.lineSeparator() + Message.WANT_TO_REPLAY);
    }

    @Override
    public void notifyException(Exception exception) {
        System.out.println(exception.getMessage());
    }

    public void askRowCoordinate() { System.out.println(Message.CHOOSE_ROW); }

    public void askColumnCoordinate() { System.out.println(Message.CHOOSE_COLUMN); }

    public void displayPlayer(Player player) {
        System.out.printf(Message.CURRENT_PLAYER,
                player.getName(),
                intersectionConsoleRepresentation.getStoneValue(player.getColor())
        );
    }

    @Override
    public void notifyPass(Player currentPlayer) { System.out.printf(Message.PASS_TURN, currentPlayer.getName()); }

    @Override
    public void notifyPieRule(List<Player> players) {
        System.out.printf(Message.PIE, players.get(0).getName(), intersectionConsoleRepresentation.getStoneValue(players.get(0).getColor()),
                players.get(1).getName(), intersectionConsoleRepresentation.getStoneValue(players.get(1).getColor()));
    }

    @Override
    public void notifyWinner(Player player) { System.out.printf(Message.END_GAME, player.getName(), intersectionConsoleRepresentation.getStoneValue(player.getColor())); }

    public void askPie(String whitePlayerName) { System.out.printf(Message.QUERY_PIE, whitePlayerName); }

    private void displayIntersection(Color color){
        System.out.print(intersectionConsoleRepresentation.getStoneValue(color));
    }

    private void displayRow(Board board, int rowIndex) {
        System.out.print(rowIndex < 10 ? rowIndex + "   " : rowIndex + "  ");
        System.out.print("W");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEach(columnIndex ->
                        displayIntersection(board.intersectionAt(Position.in(rowIndex, columnIndex)).getColor().orElse(null))
                );
        System.out.print("W" + System.lineSeparator());
    }

    public void displayBoard(Board board) {
        System.out.println("    " + "  B".repeat(board.getBoardSize()));
        IntStream.rangeClosed(1, board.getBoardSize()).forEach(rowIndex -> displayRow(board, rowIndex));
        System.out.print("    " + "  B".repeat(board.getBoardSize()) + System.lineSeparator() + "    ");
        IntStream.rangeClosed(1, board.getBoardSize())
                .forEachOrdered(columnIndex -> System.out.print(columnIndex < 10 ? "  " + columnIndex : " " + columnIndex));
        System.out.println();
    }
}