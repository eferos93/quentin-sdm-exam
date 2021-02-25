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

    private static final int TILESIZE = 50;
    private Stage stage;
    private GridPane gridPane;
    private GUIQuentin guiQuentin;
    private GUIBoardDisplayer boardFiller;

    public GridPane getGridBoard() {
        GridPane borders = (GridPane) gridPane.getChildrenUnmodifiable().get(0);
        return (GridPane) borders.getChildren().get(0);
    }

    public GridPane getLabelBoard() { return (GridPane) gridPane.getChildren().get(1); }
    public GUIQuentin getGame() { return guiQuentin; }
    public GUIBoardDisplayer getBoardFiller() { return boardFiller; }

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
        boardFiller = new GUIBoardDisplayer(boardSize, TILESIZE);

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
        return (int)(coordinate - 1) / TILESIZE;
    }

    private Button createAndSetButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefWidth(80);
        button.setPrefHeight(35);
        button.setOnAction(handler);
        return button;
    }

    private Stream<Button> initialButtons(){

        Button startButton = createAndSetButton("Start", (ActionEvent e) -> {
            stage.close();
            GUIInputHandler guiInputHandler = new GUIInputHandler();
            int size = guiInputHandler.askSize();
            String namePlayer1 = guiInputHandler.askPlayerName("1");
            String namePlayer2 = guiInputHandler.askPlayerName("2");

            initGameInterface(size, namePlayer1, namePlayer2);
        });

        Button endButton = createAndSetButton("Exit", (ActionEvent e) -> stop());

        Button rulesButton = createAndSetButton("Rules", (ActionEvent e) -> getHostServices().showDocument("https://boardgamegeek.com/boardgame/124095/quentin"));
        return Stream.of(startButton, endButton, rulesButton);
    }

    private Stream<Button> replayButtons(){
        Button yesButton = createAndSetButton("Yes", (ActionEvent e) -> initUI());
        Button noButton = createAndSetButton("No", (ActionEvent e) -> stop());
        return Stream.of(yesButton, noButton);
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

        stage.setTitle("Quentin");
        stage.setScene(scene);
        stage.show();
    }

    private void initUI() {
        structureUI(initialButtons(), "Quentin Game", 40, 15);
    }

    public void endUI() {
        structureUI(replayButtons(), "Do you wanna play again?", 20, 100);
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