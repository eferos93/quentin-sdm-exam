package quentin.UI.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import quentin.UI.GUI.Events.*;
import quentin.GUIQuentin;

import java.util.stream.Stream;

public class GUI extends Application {

    public static final int TILE_SIZE = 50;
    private GUIQuentin guiQuentin;
    private GUIBoardDisplayer guiBoardDisplayer;

    public GUIQuentin getGame() { return guiQuentin; }

    public void switchColorPlayerLabel() {
        guiBoardDisplayer.switchColorPlayerLabel();
    }

    @Override
    public void start(Stage primaryStage) {
        guiBoardDisplayer = new GUIBoardDisplayer();
        guiBoardDisplayer.initialiseGUI(initialButtons(), primaryStage);
    }

    @Override
    public void stop() { Platform.exit(); }

    public void updateGUI() {
        guiBoardDisplayer.updateIntersections(guiQuentin.getNonEmptyIntersections());
    }

    public void firePieRuleIfConditionsAreMet() {
        if (guiQuentin.askPlayerForPieRule()) {
            fireEvent(EventFactory.createPieRuleEvent(this.guiBoardDisplayer));
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
        guiBoardDisplayer.fireEvent(event);
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

    private void initGame(int boardSize, String blackPlayerName, String whitePlayerName, GUIInputHandler guiInputHandler) {
        guiQuentin = new GUIQuentin(boardSize, guiInputHandler, new GUIOutputHandler(), blackPlayerName, whitePlayerName);
        guiBoardDisplayer.initialiseGridPanel(blackPlayerName, whitePlayerName, boardSize);
        guiBoardDisplayer.addGridEvents(this, guiQuentin);
        guiBoardDisplayer.setGameStage();
    }

    private Stream<Button> replayButtons(){
        Button yesButton = createNewButton("Yes", (ActionEvent e) -> guiBoardDisplayer.initUI(initialButtons()));
        Button noButton = createNewButton("No", (ActionEvent e) -> stop());
        return Stream.of(yesButton, noButton);
    }

    public void endUI() {
        guiBoardDisplayer.replay(replayButtons());
    }
}