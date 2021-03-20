package quentin.ui.gui;

import javafx.scene.control.*;
import quentin.ui.InputHandler;
import quentin.ui.OutputHandler;
import java.util.List;
import java.util.Optional;

public class GUIInputHandler implements InputHandler {

    @Override
    public boolean askPie(String whitePlayerName){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pie Rule Dialog");
        alert.setContentText(String.format(OutputHandler.Message.QUERY_PIE, whitePlayerName));
        alert.setHeaderText(null);

        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }

    protected int askSize() {
        List<Integer> sizes = List.of(4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(13, sizes);

        dialog.setTitle("Enter Size");
        dialog.setHeaderText(null);
        dialog.setContentText(OutputHandler.Message.ASK_SIZE);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);

        return dialog.showAndWait().orElse(dialog.getDefaultChoice());
    }

    protected String askPlayerName(String playerNumber) {

        TextInputDialog dialog = new TextInputDialog("Player ".concat(playerNumber));
        dialog.setTitle("Enter name player " + playerNumber);
        dialog.setHeaderText(null);

        String message = playerNumber.equals("1") ?
                OutputHandler.Message.ASK_BLACK_PLAYER_NAME :
                OutputHandler.Message.ASK_WHITE_PLAYER_NAME;

        dialog.setContentText(message);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);

        String result = dialog.showAndWait().orElse("Player ".concat(playerNumber));
        return result.equals("") ? "Player ".concat(playerNumber) : result;
    }
}

