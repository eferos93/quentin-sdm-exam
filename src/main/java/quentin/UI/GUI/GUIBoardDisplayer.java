package quentin.UI.GUI;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import quentin.core.Stone;

import java.util.EnumMap;
import java.util.stream.IntStream;

public class GUIBoardDisplayer {

    private final int boardSize;
    private final int tileSize;
    private static final EnumMap<Stone, Paint> colorPaintMap = new EnumMap<> (Stone.class);

    GUIBoardDisplayer(int boardSize, int tileSize) {
        this.boardSize = boardSize;
        this.tileSize = tileSize;

        colorPaintMap.put(Stone.BLACK, Color.BLACK);
        colorPaintMap.put(Stone.WHITE, Color.WHITE);
    }

    public GridPane createEmptyBoard() {

        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #3CB371");

        IntStream.range(0, boardSize).forEach(row ->
                IntStream.range(0, boardSize).forEach(column ->
                        {
                            Line verticalLine = lineGenerator(0,tileSize);

                            GridPane.setHalignment(verticalLine, HPos.CENTER);
                            GridPane.setValignment(verticalLine, VPos.CENTER);

                            Line horizontalLine = lineGenerator(tileSize,0);

                            gridPane.add(horizontalLine, row, column);
                            gridPane.add(verticalLine, row, column);
                        }));

        return gridPane;
    }

    private Line lineGenerator(int endX,int endY){
        Line generatedLine = new Line(0, 0, endX, endY);
        generatedLine.setStroke(Color.BLACK);
        generatedLine.setStrokeWidth(2.0);
        return generatedLine;
    }

    public void addPiece(GridPane gridPane, int coordinateX, int coordinateY, Stone stone) {
        Circle piece = new Circle(coordinateX * tileSize, coordinateY * tileSize, tileSize * 0.4);
        piece.setFill(colorPaintMap.get(stone));
        GridPane.setHalignment(piece, HPos.CENTER);
        GridPane.setValignment(piece, VPos.CENTER);
        gridPane.add(piece, coordinateX, coordinateY);
    }

    public GridPane createLabelPane(String namePLayerOne, String namePlayerTwo) {
        GridPane gridLabels = new GridPane();
        gridLabels.setVgap(10);

        Text p1 = new Text(namePLayerOne + ": ");
        Text p2 = new Text(namePlayerTwo + ": ");
        Text currentPlayer = new Text("Now playing: ");

        gridLabels.add(p1, 0, 0);
        gridLabels.add(p2, 0, 1);
        gridLabels.add(currentPlayer, 0, 2);

        Circle whitePlayerLabel = new Circle(tileSize * 0.1, Color.BLACK);
        Circle blackPlayerLabel = new Circle(tileSize * 0.1, Color.WHITE);
        Circle currentPlayerLabel = new Circle(tileSize * 0.1, Color.BLACK);

        gridLabels.add(whitePlayerLabel, 1, 0);
        gridLabels.add(blackPlayerLabel, 1, 1);
        gridLabels.add(currentPlayerLabel, 1, 2);

        return gridLabels;
    }

    public void switchLabelsColors(GridPane labelBoard){
        Circle currentBlackPlayerLabel = (Circle) labelBoard.getChildren().get(3);
        currentBlackPlayerLabel.setFill(Color.WHITE);

        Circle currentWhitePlayerLabel = (Circle) labelBoard.getChildren().get(4);
        currentWhitePlayerLabel.setFill(Color.BLACK);
    }

    public void switchLabelsCurrentPlayer(GridPane labelBoard ){
        Circle currentPlayerLabel = (Circle) labelBoard.getChildren().get(5);
        currentPlayerLabel.setFill(currentPlayerLabel.getFill() == Color.BLACK ? Color.WHITE : Color.BLACK);
    }
}