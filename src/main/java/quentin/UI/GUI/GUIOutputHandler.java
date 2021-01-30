package quentin.UI.GUI;

import javafx.scene.control.Alert;
import quentin.UI.OutputHandler;
import quentin.core.Board;
import quentin.core.Player;

import java.util.List;

public class GUIOutputHandler implements OutputHandler {

    private static void createAndSetAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @Override
    public void askRowCoordinate() {
        createAndSetAlert("Row Coordinate Information", Message.CHOOSE_ROW);
    }

    @Override
    public void askColumnCoordinate() {
        createAndSetAlert("Column Coordinate Information", Message.CHOOSE_COLUMN);
    }

    @Override
    public void displayPlayer(Player player) {
        createAndSetAlert("Current Player Information", Message.CURRENT_PLAYER);
    }

    @Override
    public void notifyPass(Player currentPlayer) {
        createAndSetAlert("Pass Rule Information", Message.PASS_TURN);
    }

    @Override
    public void notifyPieRule(List<Player> players) {
        createAndSetAlert("Pie Rule Information", Message.PIE);
    }

    @Override
    public void notifyWinner(Player player) {
        createAndSetAlert("Winner Information", Message.END_GAME);
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
