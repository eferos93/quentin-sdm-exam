package quentin.UI.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import quentin.UI.OutputHandler;
import quentin.UI.console.ConsoleStoneRepresentation;
import quentin.core.Board;
import quentin.core.Player;

import java.util.List;

public class GUIOutputHandler implements OutputHandler {

    private static void createAndSetAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, contentText, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.show();
    }

    public static void notifyException(String message) {
        createAndSetAlert("Invalid Action Information", String.format("%s", message));
    }

    public void notifyInvalidMove() { //TODO: not sure it used
        createAndSetAlert("Invalid Move Information", String.format("%s", Message.INVALID_MOVE));
    }

    @Override
    public void notifyPass(Player currentPlayer) {

        String message = String.format(Message.PASS_TURN, currentPlayer.getName());
        createAndSetAlert("Pass Rule Information", String.format("%s", message));
    }

    @Override
    public void notifyPieRule(List<Player> players) {

        String message = String.format(Message.PIE, players.get(0).getName(), ConsoleStoneRepresentation.getStoneValue(players.get(0).getColor()),
                players.get(1).getName(), ConsoleStoneRepresentation.getStoneValue(players.get(1).getColor()));

        createAndSetAlert("Pie Rule Information", String.format("%s", message));
    }

    @Override
    public void notifyWinner(Player player) {

        String message = String.format(Message.END_GAME, player.getName(),
                ConsoleStoneRepresentation.getStoneValue(player.getColor()));

        createAndSetAlert("Winner Information", String.format("%s", message));
    }

    @Override
    public void askPie() {
        createAndSetAlert("Pie Rule Information", Message.QUERY_PIE);
    }

    @Override
    public void displayBoard(Board board) {
        createAndSetAlert("Board Information", Message.INSTRUCTIONS);
    }
}
