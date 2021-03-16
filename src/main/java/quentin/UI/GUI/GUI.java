package quentin.UI.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import quentin.UI.GUI.Events.*;
import quentin.UI.GUI.Handlers.*;
import quentin.GUIQuentin;

import java.util.stream.Stream;

public class GUI extends Application {

    public static final int TILE_SIZE = 50;
    private Stage stage;
    private GridPane gridPanel;
    private GUIQuentin guiQuentin;
    private GUIBoardDisplayer boardFiller;

    private GridPane getGridBoard() {
        GridPane borders = (GridPane) gridPanel.getChildrenUnmodifiable().get(0);
        return (GridPane) borders.getChildren().get(0);
    }

    private GridPane getLabelBoard() { return (GridPane) gridPanel.getChildren().get(1); }
    public GUIQuentin getGame() { return guiQuentin; }

    public void switchColorPlayerLabel() {
        boardFiller.switchColorPlayerLabel(getLabelBoard());
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setResizable(false);
        initUI();
    }

    private void addGridEvent(GridPane gridBoard) {
        gridBoard.addEventHandler(PieRuleEvent.PIE_RULE_EVENT_TYPE, new GuiPieHandler(this));
        gridBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, new GuiMouseHandler(this));
        gridBoard.addEventHandler(PassEvent.PASS_EVENT_TYPE, new GuiPassHandler(guiQuentin));
        gridBoard.addEventHandler(EndGameEvent.END_GAME_EVENT_TYPE, new GuiEndGameHandler(this));
    }

    private void initGridPanel(String blackPlayerName, String whitePlayerName){
        gridPanel = new GridPane();
        gridPanel.setVgap(20);

        boardFiller.createEmptyBoard();
        GridPane borders = new GridPane();
        borders.getStyleClass().add("borders");
        borders.add(boardFiller.getGridBoard(), 0, 0);

        GridPane labelBoard = boardFiller.createLabelPane(blackPlayerName, whitePlayerName);
        labelBoard.getStyleClass().add("label-board");

        gridPanel.add(borders, 0, 0);
        gridPanel.add(labelBoard, 0, 1);
        gridPanel.getStyleClass().add("grid-pane");
    }

    private void initGame(int boardSize, String blackPlayerName, String whitePlayerName, GUIInputHandler guiInputHandler) {

        guiQuentin = new GUIQuentin(boardSize, guiInputHandler, new GUIOutputHandler(), blackPlayerName, whitePlayerName);
        boardFiller = new GUIBoardDisplayer(boardSize, TILE_SIZE);

        initGridPanel(blackPlayerName, whitePlayerName);
        addGridEvent(boardFiller.getGridBoard());

        setGameStage();
    }

    private void setGameStage() {
        Scene scene = new Scene(gridPanel, Color.WHITESMOKE);
        String path = getClass().getResource("/GUI.css").toExternalForm();
        scene.getStylesheets().add(path);
        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();
    }

    private Button createNewButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setPrefWidth(80);
        button.setPrefHeight(35);
        button.setOnAction(handler);
        return button;
    }

    private Stream<Button> initialButtons(){

        Button startButton = createNewButton("Start", (ActionEvent e) -> {
            stage.close();
            GUIInputHandler guiInputHandler = new GUIInputHandler();
            int size = guiInputHandler.askSize();
            String namePlayer1 = guiInputHandler.askPlayerName("1");
            String namePlayer2 = guiInputHandler.askPlayerName("2");

            initGame(size, namePlayer1, namePlayer2, guiInputHandler);
        });

        Button endButton = createNewButton("Exit", (ActionEvent e) -> stop());

        Button rulesButton = createNewButton("Rules", (ActionEvent e) -> getHostServices().showDocument("https://boardgamegeek.com/boardgame/124095/quentin"));
        return Stream.of(startButton, endButton, rulesButton);
    }

    private Stream<Button> replayButtons(){
        Button yesButton = createNewButton("Yes", (ActionEvent e) -> initUI());
        Button noButton = createNewButton("No", (ActionEvent e) -> stop());
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

    @Override
    public void stop() { Platform.exit(); }

    public void updateGUI() {
        guiQuentin.getNonEmptyIntersections()
                .forEach(nonEmptyIntersection ->
                        boardFiller.addPiece(
                                nonEmptyIntersection.getPosition(),
                                nonEmptyIntersection.getColor().orElseThrow()
                        )
                );
        boardFiller.switchLabelsCurrentPlayer(getLabelBoard());
    }

    public void firePieRuleIfConditionsAreMet() {
        if (guiQuentin.askPlayerForPieRule()) {
            fireEvent(EventFactory.createPieRuleEvent());
        }
    }

    public void fireEndGameEventIfConditionsAreMet() {
        if (guiQuentin.isGameEnded()) {
            fireEvent(EventFactory.createEndGameEvent());
        }
    }

    public void notifyException(Exception exception) {
        guiQuentin.notifyException(exception);
    }

    public void fireEvent(Event event) {
        getGridBoard().fireEvent(event);
    }
}