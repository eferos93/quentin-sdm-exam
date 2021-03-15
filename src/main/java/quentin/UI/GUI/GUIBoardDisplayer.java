package quentin.UI.GUI;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import quentin.core.Colour;
import quentin.core.Position;

import java.util.EnumMap;
import java.util.stream.IntStream;

public class GUIBoardDisplayer {

    private final int boardSize;
    private final int tileSize;
    private final EnumMap<Colour, Paint> colorPaintMap = new EnumMap<>(Colour.class);

    protected GUIBoardDisplayer(int boardSize, int tileSize) {
        this.boardSize = boardSize;
        this.tileSize = tileSize;

        colorPaintMap.put(Colour.BLACK, javafx.scene.paint.Color.BLACK);
        colorPaintMap.put(Colour.WHITE, javafx.scene.paint.Color.WHITE);
    }

    protected GridPane createEmptyBoard() {

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #3CB371");

        for (int gridCellIndex = 0; gridCellIndex < boardSize; gridCellIndex++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(tileSize));
            gridPane.getRowConstraints().add(new RowConstraints(tileSize));
        }

        createLines(gridPane);

        return gridPane;
    }

    private void createLines(GridPane gridPane) {
        IntStream.range(0, boardSize).forEach(column ->
                IntStream.range(0, boardSize).forEach(row ->
                {
                    Line verticalLine = lineGenerator(0, tileSize);
                    Line horizontalLine = lineGenerator(tileSize, 0);
                    GridPane.setHalignment(verticalLine, HPos.CENTER);
                    GridPane.setValignment(verticalLine, VPos.CENTER);

                    if (row == 0) {
                        verticalLine.setEndY(tileSize / 2.0);
                        GridPane.setValignment(verticalLine, VPos.BOTTOM);
                        horizontalLine.setStrokeWidth(4);
                    }

                    if (row == boardSize - 1) {
                        verticalLine.setEndY(tileSize / 2.0);
                        GridPane.setValignment(verticalLine, VPos.TOP);
                        horizontalLine.setStrokeWidth(4);
                    }

                    if (column == 0) {
                        horizontalLine.setEndX(tileSize / 2.0);
                        GridPane.setHalignment(horizontalLine, HPos.RIGHT);
                        verticalLine.setStrokeWidth(4);
                    }

                    if (column == boardSize - 1) {
                        horizontalLine.setEndX(tileSize / 2.0);
                        GridPane.setHalignment(horizontalLine, HPos.LEFT);
                        verticalLine.setStrokeWidth(4);
                    }

                    gridPane.add(horizontalLine, column, row);
                    gridPane.add(verticalLine, column, row);
                }));
    }

    private Line lineGenerator(int endX, int endY) {
        Line generatedLine = new Line(0, 0, endX, endY);
        generatedLine.setStroke(javafx.scene.paint.Color.BLACK);
        generatedLine.setStrokeWidth(2.0);
        return generatedLine;
    }

    private int getConvertedCoordinate(int coordinate) {
        return coordinate - 1;
    }

    protected void addPiece(GridPane gridPane, Position position, Colour colour) {
        Circle piece = new Circle((getConvertedCoordinate(position.getColumn())) * tileSize,
                (getConvertedCoordinate(position.getRow())) * tileSize, tileSize * 0.4);
        piece.setFill(colorPaintMap.get(colour));
        GridPane.setHalignment(piece, HPos.CENTER);
        GridPane.setValignment(piece, VPos.CENTER);
        gridPane.add(piece, getConvertedCoordinate(position.getColumn()), getConvertedCoordinate(position.getRow()));
    }

    protected GridPane createLabelPane(String namePLayerOne, String namePlayerTwo) {
        GridPane gridLabels = new GridPane();
        gridLabels.setVgap(10);

        gridLabels.add(new Text(namePLayerOne + ": "), 0, 0);
        gridLabels.add(new Text(namePlayerTwo + ": "), 0, 1);
        gridLabels.add(new Text("Now playing: "), 0, 2);

        Circle whitePlayerLabel = new Circle(tileSize * 0.1, javafx.scene.paint.Color.WHITE);
        Circle blackPlayerLabel = new Circle(tileSize * 0.1, javafx.scene.paint.Color.BLACK);
        Circle currentPlayerLabel = new Circle(tileSize * 0.1, javafx.scene.paint.Color.BLACK);

        gridLabels.add(whitePlayerLabel, 1, 1);
        gridLabels.add(blackPlayerLabel, 1, 0);
        gridLabels.add(currentPlayerLabel, 1, 2);

        return gridLabels;
    }

    protected void switchColorPlayerLabel(GridPane labelBoard) {
        switchColorLabel(labelBoard, 3, javafx.scene.paint.Color.BLACK);
        switchColorLabel(labelBoard, 4, javafx.scene.paint.Color.WHITE);
    }

    private void switchColorLabel(GridPane labelBoard, int position, javafx.scene.paint.Color color) {
        Circle currentPlayerLabel = (Circle) labelBoard.getChildren().get(position);
        currentPlayerLabel.setFill(color);
    }

    protected void switchLabelsCurrentPlayer(GridPane labelBoard) {
        Circle currentPlayerLabel = (Circle) labelBoard.getChildren().get(5);
        currentPlayerLabel.setFill(currentPlayerLabel.getFill() == javafx.scene.paint.Color.BLACK ? javafx.scene.paint.Color.WHITE : javafx.scene.paint.Color.BLACK);
    }
}