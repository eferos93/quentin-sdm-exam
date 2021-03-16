package quentin.UI.GUI;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import quentin.core.Colour;
import quentin.core.Position;

import java.util.EnumMap;
import java.util.stream.IntStream;

public class GUIBoardDisplayer {

    private final GridPane gridBoard = new GridPane();
    private final int boardSize;
    private final int tileSize;
    private final EnumMap<Colour, Paint> colorPaintMap = new EnumMap<>(Colour.class);

    protected GUIBoardDisplayer(int boardSize, int tileSize) {
        this.boardSize = boardSize;
        this.tileSize = tileSize;
        colorPaintMap.put(Colour.BLACK, Color.BLACK);
        colorPaintMap.put(Colour.WHITE, Color.WHITE);
        gridBoard.setStyle("-fx-background-color: #3CB371");
    }

    protected void createEmptyBoard() {

        for (int gridCellIndex = 0; gridCellIndex < boardSize; gridCellIndex++) {
            gridBoard.getColumnConstraints().add(new ColumnConstraints(tileSize));
            gridBoard.getRowConstraints().add(new RowConstraints(tileSize));
        }
        createLines();
    }

    protected GridPane getGridBoard(){return this.gridBoard;}

    private void createLines() {
        IntStream.range(0, boardSize).forEach(column ->
                IntStream.range(0, boardSize).forEach(row ->
                {
                    Line verticalLine = lineGenerator(0, tileSize);
                    Line horizontalLine = lineGenerator(tileSize, 0);
                    GridPane.setHalignment(verticalLine, HPos.CENTER);
                    GridPane.setValignment(verticalLine, VPos.CENTER);
                    placeLines(column, row, verticalLine, horizontalLine);
                    gridBoard.add(horizontalLine, column, row);
                    gridBoard.add(verticalLine, column, row);
                }));
    }

    private void placeLines(int column, int row, Line verticalLine, Line horizontalLine) {
        if (row == 0) { verticalLinePlacement(verticalLine, VPos.BOTTOM); }
        if (row == boardSize - 1) { verticalLinePlacement(verticalLine, VPos.TOP); }
        if (column == 0) { horizontalLinePlacement(horizontalLine, HPos.RIGHT); }
        if (column == boardSize - 1) { horizontalLinePlacement(horizontalLine, HPos.LEFT); }
    }

    private void verticalLinePlacement(Line line, VPos position){
        line.setEndY(tileSize / 2.0);
        GridPane.setValignment(line, position);
        line.setStrokeWidth(2);
    }

    private void horizontalLinePlacement(Line line, HPos position){
        line.setEndX(tileSize / 2.0);
        GridPane.setHalignment(line, position);
        line.setStrokeWidth(2);
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

    protected void addPiece(Position position, Colour colour) {
        Circle piece = new Circle((getConvertedCoordinate(position.getColumn())) * tileSize,
                (getConvertedCoordinate(position.getRow())) * tileSize, tileSize * 0.4);
        piece.setFill(colorPaintMap.get(colour));
        GridPane.setHalignment(piece, HPos.CENTER);
        GridPane.setValignment(piece, VPos.CENTER);
        gridBoard.add(piece, getConvertedCoordinate(position.getColumn()), getConvertedCoordinate(position.getRow()));
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
        currentPlayerLabel.setFill(getOppositeColor(currentPlayerLabel.getFill()));
    }

    private Color getOppositeColor(Paint currentPlayerLabel) {
        return currentPlayerLabel == Color.BLACK ? Color.WHITE : Color.BLACK;
    }
}