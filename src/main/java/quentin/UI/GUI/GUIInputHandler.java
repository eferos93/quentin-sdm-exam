package quentin.UI.GUI;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;

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
}

