package quentin.UI.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import quentin.UI.OutputHandler;
import quentin.core.Board;
import quentin.core.Player;

import java.util.List;

public class GUIOutputHandler implements OutputHandler {

    private void createAndSetAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, contentText, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @Override
    public void notifyException(String exceptionMessage) {
        createAndSetAlert("Invalid Action Information", String.format("%s", exceptionMessage));
    }

    public void notifyInvalidMove() { //TODO: not sure it used
        createAndSetAlert("Invalid Move Information", String.format("%s", Message.INVALID_MOVE));
    }

    @Override
    public void notifyPass(Player currentPlayer) {
        String message = String.format(Message.PASS_TURN, currentPlayer.getName());
        createAndSetAlert("Pass Rule Information", message);
    }

    @Override
    public void notifyPieRule(List<Player> players) {

        String message = String.format(Message.PIE,
                players.get(0).getName(), players.get(0).getColor().name(),
                players.get(1).getName(), players.get(1).getColor().name());

        createAndSetAlert("Pie Rule Information", message);
    }

    @Override
    public void notifyWinner(Player player) {
        String message = String.format(Message.END_GAME, player.getName(), player.getColor().name());
        createAndSetAlert("Winner Information", message);
    }

    //TODO: this method is not used in GUI, might think to remove it from here and from abstraction
    @Override
    public void askPie(String whitePlayerName) {
        createAndSetAlert("Pie Rule Information", String.format(Message.QUERY_PIE, whitePlayerName));
    }

    @Override
    public void displayBoard(Board board) {
        createAndSetAlert("Board Information", Message.INSTRUCTIONS);
    }
}
