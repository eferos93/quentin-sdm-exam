package quentin.UI.GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class LabelHandler {

    protected GridPane createLabelPane(String namePLayerOne, String namePlayerTwo, int tileSize) {
        GridPane gridLabels = new GridPane();
        gridLabels.setVgap(10);

        gridLabels.add(new Text(namePLayerOne + ": "), 0, 0);
        gridLabels.add(new Text(namePlayerTwo + ": "), 0, 1);
        gridLabels.add(new Text("Now playing: "), 0, 2);

        Circle whitePlayerLabel = new Circle(tileSize * 0.1, Color.WHITE);
        Circle blackPlayerLabel = new Circle(tileSize * 0.1, Color.BLACK);
        Circle currentPlayerLabel = new Circle(tileSize * 0.1, Color.BLACK);

        gridLabels.add(whitePlayerLabel, 1, 1);
        gridLabels.add(blackPlayerLabel, 1, 0);
        gridLabels.add(currentPlayerLabel, 1, 2);

        return gridLabels;
    }

    protected void switchColorPlayerLabel(GridPane labelBoard) {
        switchColorLabel(labelBoard, 3, Color.BLACK);
        switchColorLabel(labelBoard, 4, Color.WHITE);
    }

    private void switchColorLabel(GridPane labelBoard, int position, Color color) {
        Circle currentPlayerLabel = (Circle) labelBoard.getChildren().get(position);
        currentPlayerLabel.setFill(color);
    }

    protected void switchLabelsCurrentPlayer(GridPane labelBoard) {
        Circle currentPlayerLabel = (Circle) labelBoard.getChildren().get(5);
        currentPlayerLabel.setFill(getOppositeColor(currentPlayerLabel.getFill()));
    }

    protected Color getOppositeColor(Paint currentPlayerLabel) {
        return currentPlayerLabel == Color.BLACK ? Color.WHITE : Color.BLACK;
    }
}