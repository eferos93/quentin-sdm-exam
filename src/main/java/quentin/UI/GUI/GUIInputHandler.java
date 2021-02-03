package quentin.UI.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class GUIInputHandler implements InputHandler {

    public boolean askPie(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pie Rule Dialog");
        alert.setContentText(OutputHandler.Message.QUERY_PIE);
        alert.setHeaderText(null);

        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }

    public int askSize() {
        ArrayList<Integer> sizes = new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11,12,13));
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(13, sizes);

        dialog.setTitle("Enter Size");
        dialog.setHeaderText(null);
        dialog.setContentText(OutputHandler.Message.ASK_SIZE);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);

        Optional<Integer> result = dialog.showAndWait();
        return result.orElse(13);
    }
}

