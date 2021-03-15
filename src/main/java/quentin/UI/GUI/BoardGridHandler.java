package quentin.UI.GUI;

import javafx.scene.layout.GridPane;
import quentin.GUIQuentin;

public class BoardGridHandler {
    private GridPane gridPane;
    private GUIBoardDisplayer boardFiller;

    protected GridPane getGridBoard() {
        GridPane borders = (GridPane) gridPane.getChildrenUnmodifiable().get(0);
        return (GridPane) borders.getChildren().get(0);
    }

    protected GridPane getGridPane() {
        return gridPane;
    }

    protected void setBoardFiller(GUIBoardDisplayer guiBoardDisplayer) {
        this.boardFiller = guiBoardDisplayer;
    }

    protected GUIBoardDisplayer getBoardFiller() {return boardFiller;}

    protected GridPane getLabelBoard() {
        return (GridPane) gridPane.getChildren().get(1);
    }

    protected void switchColorPlayerLabel() {
        boardFiller.switchColorPlayerLabel(getLabelBoard());
    }

    protected GridPane createGridBoard(GUIQuentin guiQuentin) {
        gridPane = new GridPane();
        gridPane.setVgap(20);

        GridPane gridBoard = boardFiller.createEmptyBoard();
        GridPane borders = new GridPane();
        borders.getStyleClass().add("borders");
        borders.add(gridBoard, 0, 0);

        GridPane labelBoard = boardFiller.createLabelPane(
                guiQuentin.getCurrentPlayer().getName(),
                guiQuentin.getLastPlayer().getName());
        labelBoard.getStyleClass().add("label-board");

        gridPane.add(borders, 0, 0);
        gridPane.add(labelBoard, 0, 1);
        gridPane.getStyleClass().add("grid-pane");

        return gridBoard;
    }
}