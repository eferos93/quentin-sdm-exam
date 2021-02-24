package quentin.UI.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quentin.UI.GUI.Events.EndGameEvent;
import quentin.UI.GUI.Events.EventFactory;
import quentin.UI.GUI.Events.PassEvent;
import quentin.UI.GUI.Events.PieRuleEvent;
import quentin.UI.GUI.Handlers.GuiEndGameHandler;
import quentin.UI.GUI.Handlers.GuiMouseHandler;
import quentin.UI.GUI.Handlers.GuiPassHandler;
import quentin.UI.GUI.Handlers.GuiPieHandler;
import quentin.GUIQuentin;
import quentin.core.Intersection;
import quentin.core.Player;

import java.util.stream.Stream;

public class GUI extends Application {

    private static final int tileSize = 50;
    private Stage stage;
    private GridPane gridPane;
    private GUIQuentin guiQuentin;
    private GUIBoardDisplayer boardFiller;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setResizable(false);
        initUI();
    }

    private void addGridEvent(GridPane gridBoard) {
        gridBoard.addEventHandler(PieRuleEvent.PIE_RULE_EVENT_TYPE, new GuiPieHandler(this));
        gridBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, new GuiMouseHandler(this));
        gridBoard.addEventHandler(PassEvent.PASS_EVENT_TYPE, new GuiPassHandler(this));
        gridBoard.addEventHandler(EndGameEvent.END_GAME_EVENT_TYPE, new GuiEndGameHandler(this));
    }

    private GridPane createGridBoard(){
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

    private void initGameInterface(int boardSize, String namePlayer1, String namePlayer2) {

        guiQuentin = new GUIQuentin(boardSize,new GUIInputHandler(), new GUIOutputHandler(), namePlayer1, namePlayer2);
        boardFiller = new GUIBoardDisplayer(boardSize, tileSize);

        GridPane gridBoard = createGridBoard();
        addGridEvent(gridBoard);

        Scene scene = new Scene(gridPane, Color.WHITESMOKE);

        String path = getClass().getResource("/GUI.css").toExternalForm();
        scene.getStylesheets().add(path);

        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();
    }

    public int coordinateConversion(double coordinate) {
        return (int)(coordinate - 1) / tileSize;
    }

    private Button createAndSetButton(String text, int width, int height, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setOnAction(handler);
        return button;
    }

    private Stream<Button> createSetButtons(int width, int height){

        Button startButton = createAndSetButton("Start", width, height, (ActionEvent e) -> {
            GUIInputHandler guiInputHandler = new GUIInputHandler();
            int size = guiInputHandler.askSize();
            String namePlayer1 = guiInputHandler.askPlayerName("1");
            String namePlayer2 = guiInputHandler.askPlayerName("2");

            initGameInterface(size, namePlayer1, namePlayer2);
        });

        Button endButton = createAndSetButton("Exit", width, height, (ActionEvent e) -> stop());

        Button rulesButton = createAndSetButton("Rules", width, height, (ActionEvent e) -> getHostServices().showDocument("https://boardgamegeek.com/boardgame/124095/quentin"));
        return Stream.of(startButton, endButton, rulesButton);
    }

    private void initUI() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setVgap(20);

        Text text = new Text("Quentin Game");
        text.setFont(Font.font("Tahoma", 40));

        pane.add(text, 0, 0 );
        GridPane.setHalignment(text, HPos.CENTER);

        Stream<Button> buttonStream = createSetButtons(80,35);

        HBox hBox = new HBox();
        buttonStream.forEach(button -> hBox.getChildren().add(button));

        pane.add(hBox, 0, 1);
        hBox.setSpacing(15);
        GridPane.setHalignment(hBox, HPos.CENTER);

        Scene scene = new Scene(pane);

        stage.setTitle("Quentin");
        stage.setScene(scene);
        stage.show();
    }

    public void fillGridBoardWithTerritories() {
        guiQuentin.getTerritoriesAndStones(guiQuentin.getLastPlay()).forEach((territory, stone) ->
                territory.stream().map(Intersection::getPosition).
                        forEach(position -> this.getBoardFiller().
                                addPiece(this.getGridBoard(),
                                        position.getColumn() - 1, // to be consistent with the board gui representation
                                        position.getRow() - 1, // to be consistent with the board gui representation
                                        stone)));
    }

    @Override
    public void stop() { Platform.exit(); }

    public GridPane getGridBoard() {
        GridPane borders = (GridPane) gridPane.getChildrenUnmodifiable().get(0);
        return (GridPane) borders.getChildren().get(0);
    }

    public GridPane getLabelBoard() { return (GridPane) gridPane.getChildren().get(1); }
    public GUIQuentin getGame() { return guiQuentin; }
    public GUIBoardDisplayer getBoardFiller() { return boardFiller; }

    public void updateGUIAndFireEvents(int columnIndex, int rowIndex, Player currentPlayer) {
        getBoardFiller().addPiece(getGridBoard(), columnIndex, rowIndex, currentPlayer.getColor());
        fillGridBoardWithTerritories();
        getGame().fillTerritories();
        getBoardFiller().switchLabelsCurrentPlayer(getLabelBoard());
        if (guiQuentin.checkAndPerformPieRule()) {
            getGridBoard().fireEvent(EventFactory.createPieRuleEvent());
        }
        if (guiQuentin.isCurrentPlayerNotAbleToMakeAMove()) {
            getGridBoard().fireEvent(EventFactory.createPassEvent());
        }
        if (guiQuentin.checkAndPerformEndGameRule()) {
            getGridBoard().fireEvent(EventFactory.createEndGameEvent());
        }
    }

    public void notifyException(Exception exception) {
        guiQuentin.notifyException(exception);
    }
}