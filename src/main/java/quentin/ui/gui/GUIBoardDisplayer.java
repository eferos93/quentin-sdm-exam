package quentin.ui.gui;


import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quentin.ui.gui.events.EndGameEvent;
import quentin.ui.gui.events.PassEvent;
import quentin.ui.gui.events.PieRuleEvent;
import quentin.ui.gui.handler.GuiEndGameHandler;
import quentin.ui.gui.handler.GuiMouseHandler;
import quentin.ui.gui.handler.GuiPassHandler;
import quentin.ui.gui.handler.GuiPieHandler;
import quentin.core.Colour;
import quentin.core.Intersection;
import quentin.core.Position;

import java.util.EnumMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GUIBoardDisplayer {
    private Stage stage;
    private GridPane gridPanel;
    private static final int TILE_SIZE = 50;
    private final EnumMap<Colour, Paint> colorPaintMap = new EnumMap<>(Colour.class);

    protected GUIBoardDisplayer() {
        colorPaintMap.put(Colour.BLACK, Color.BLACK);
        colorPaintMap.put(Colour.WHITE, Color.WHITE);
        gridPanel = new GridPane();
    }

    protected GridPane createEmptyBoard(int boardSize) {
        GridPane gridBoard = new GridPane();
        for (int gridCellIndex = 0; gridCellIndex < boardSize; gridCellIndex++) {
            gridBoard.getColumnConstraints().add(new ColumnConstraints(TILE_SIZE));
            gridBoard.getRowConstraints().add(new RowConstraints(TILE_SIZE));
        }
        createLines(gridBoard, boardSize);
        return gridBoard;
    }

    private void createLines(GridPane gridBoard, int boardSize) {
        IntStream.range(0, boardSize).forEach(column ->
                IntStream.range(0, boardSize).forEach(row ->
                {
                    Line verticalLine = lineGenerator(0, TILE_SIZE);
                    Line horizontalLine = lineGenerator(TILE_SIZE, 0);
                    GridPane.setHalignment(verticalLine, HPos.CENTER);
                    GridPane.setValignment(verticalLine, VPos.CENTER);
                    placeLines(column, row, verticalLine, horizontalLine, boardSize);
                    gridBoard.add(horizontalLine, column, row);
                    gridBoard.add(verticalLine, column, row);
                }));
    }

    private void placeLines(int column, int row, Line verticalLine, Line horizontalLine, int boardSize) {
        if (row == 0) { verticalLinePlacement(verticalLine, VPos.BOTTOM); }
        if (row == boardSize - 1) { verticalLinePlacement(verticalLine, VPos.TOP); }
        if (column == 0) { horizontalLinePlacement(horizontalLine, HPos.RIGHT); }
        if (column == boardSize - 1) { horizontalLinePlacement(horizontalLine, HPos.LEFT); }
    }

    private void verticalLinePlacement(Line line, VPos position){
        line.setEndY(TILE_SIZE / 2.0);
        GridPane.setValignment(line, position);
        line.setStrokeWidth(2);
    }

    private void horizontalLinePlacement(Line line, HPos position){
        line.setEndX(TILE_SIZE / 2.0);
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
        Circle piece = new Circle((getConvertedCoordinate(position.getColumn())) * TILE_SIZE,
                (getConvertedCoordinate(position.getRow())) * TILE_SIZE, TILE_SIZE * 0.4);
        piece.setFill(colorPaintMap.get(colour));
        GridPane.setHalignment(piece, HPos.CENTER);
        GridPane.setValignment(piece, VPos.CENTER);
        getGridBoard().add(piece, getConvertedCoordinate(position.getColumn()), getConvertedCoordinate(position.getRow()));
    }

    protected GridPane createLabelPane(String namePLayerOne, String namePlayerTwo) {
        GridPane gridLabels = new GridPane();
        gridLabels.setVgap(10);

        gridLabels.add(new Text(namePLayerOne + ": "), 0, 0);
        gridLabels.add(new Text(namePlayerTwo + ": "), 0, 1);
        gridLabels.add(new Text("Now playing: "), 0, 2);

        Circle whitePlayerLabel = new Circle(TILE_SIZE * 0.1, javafx.scene.paint.Color.WHITE);
        Circle blackPlayerLabel = new Circle(TILE_SIZE * 0.1, javafx.scene.paint.Color.BLACK);
        Circle currentPlayerLabel = new Circle(TILE_SIZE * 0.1, javafx.scene.paint.Color.BLACK);

        gridLabels.add(whitePlayerLabel, 1, 1);
        gridLabels.add(blackPlayerLabel, 1, 0);
        gridLabels.add(currentPlayerLabel, 1, 2);

        return gridLabels;
    }

    public void switchColorPlayerLabel() {
        switchColorLabel(getLabelBoard(), 3, javafx.scene.paint.Color.BLACK);
        switchColorLabel(getLabelBoard(), 4, javafx.scene.paint.Color.WHITE);
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

    private void structureUI(Stream<Button> buttonStream, String content, int size, int buttonsSpacing){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setVgap(20);
        Text text = new Text(content);
        text.setFont(Font.font("Tahoma", size));
        pane.add(text, 0, 0 );
        GridPane.setHalignment(text, HPos.CENTER);
        HBox hBox = new HBox();
        buttonStream.forEach(button -> hBox.getChildren().add(button));
        pane.add(hBox, 0, 1);
        hBox.setSpacing(buttonsSpacing);
        GridPane.setHalignment(hBox, HPos.CENTER);
        Scene scene = new Scene(pane);
        stage.close();
        stage.setTitle("Quentin");
        stage.setScene(scene);
        stage.show();
    }

    protected void initUI(Stream<Button> buttons) {
        structureUI(buttons, "Quentin Game", 40, 15);
    }

    public void replay(Stream<Button> buttons) {
        structureUI(buttons, "Do you wanna play again?", 20, 100);
    }



    private GridPane getGridBoard() {
        GridPane borders = (GridPane) gridPanel.getChildrenUnmodifiable().get(0);
        return (GridPane) borders.getChildren().get(0);
    }

    protected void initialiseGridPane(String blackPlayerName, String whitePlayerName, int boardSize){
        gridPanel = new GridPane();
        gridPanel.setVgap(20);

        GridPane boardPane = createEmptyBoard(boardSize);
        boardPane.getStyleClass().add("background");
        GridPane borders = new GridPane();
        borders.getStyleClass().add("borders");
        borders.add(boardPane, 0, 0);

        GridPane labelBoard = createLabelPane(blackPlayerName, whitePlayerName);
        labelBoard.getStyleClass().add("label-board");

        gridPanel.add(borders, 0, 0);
        gridPanel.add(labelBoard, 0, 1);
        gridPanel.getStyleClass().add("grid-pane");
    }

    protected void addGridEvents(GUI gui) {
        getGridBoard().addEventHandler(PieRuleEvent.PIE_RULE_EVENT_TYPE, new GuiPieHandler());
        getGridBoard().addEventHandler(MouseEvent.MOUSE_CLICKED, new GuiMouseHandler(gui));
        getGridBoard().addEventHandler(PassEvent.PASS_EVENT_TYPE, new GuiPassHandler());
        getGridBoard().addEventHandler(EndGameEvent.END_GAME_EVENT_TYPE, new GuiEndGameHandler());
    }

    protected void setGameStage() {
        Scene scene = new Scene(gridPanel, Color.WHITESMOKE);
        String path = getClass().getResource("/GUI.css").toExternalForm();
        scene.getStylesheets().add(path);
        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane getLabelBoard() { return (GridPane) gridPanel.getChildren().get(1); }

    public void initialiseGUI(Stream<Button> buttons, Stage primaryStage) {
        stage = primaryStage;
        stage.setResizable(false);
        initUI(buttons);
    }

    public void fireEvent(Event event) {
        getGridBoard().fireEvent(event);
    }

    public void updateIntersections(Stream<Intersection> nonEmptyIntersections) {
        nonEmptyIntersections.forEach(nonEmptyIntersection ->
                        addPiece(
                                nonEmptyIntersection.getPosition(),
                                nonEmptyIntersection.getColour().orElseThrow()
                        )
                );
        switchLabelsCurrentPlayer(getLabelBoard());
    }
}