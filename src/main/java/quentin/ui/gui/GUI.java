package quentin.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import quentin.ui.gui.events.*;
import quentin.GUIQuentin;

import java.util.stream.Stream;

public class GUI extends Application {

    public static final int TILE_SIZE = 50;
    private GUIQuentin guiQuentin;
    private GUIBoardDisplayer guiBoardDisplayer;

    public GUIQuentin getGame() { return guiQuentin; }

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
            fireEvent(EventFactory.createEndGameEvent(this));
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
        Button startButton = createNewButton("Start", actionEvent -> {
            GUIInputHandler guiInputHandler = new GUIInputHandler();
            int size = guiInputHandler.askSize();
            String namePlayer1 = guiInputHandler.askPlayerName("1");
            String namePlayer2 = guiInputHandler.askPlayerName("2");
            initGame(size, namePlayer1, namePlayer2, guiInputHandler);
        });

        Button endButton = createNewButton("Exit", actionEvent -> stop());

        Button rulesButton = createNewButton("Rules", actionEvent -> getHostServices().showDocument("https://boardgamegeek.com/boardgame/124095/quentin"));
        return Stream.of(startButton, endButton, rulesButton);
    }

    private void initGame(int boardSize, String blackPlayerName, String whitePlayerName, GUIInputHandler guiInputHandler) {
        guiQuentin = new GUIQuentin(boardSize, guiInputHandler, new GUIOutputHandler(), blackPlayerName, whitePlayerName);
        guiBoardDisplayer.initialiseGridPane(blackPlayerName, whitePlayerName, boardSize);
        guiBoardDisplayer.addGridEvents(this);
        guiBoardDisplayer.setGameStage();
    }

    private Stream<Button> replayButtons(){
        Button yesButton = createNewButton("Yes", actionEvent -> guiBoardDisplayer.initUI(initialButtons()));
        Button noButton = createNewButton("No", actionEvent -> stop());
        return Stream.of(yesButton, noButton);
    }

    public void endUI() {
        guiBoardDisplayer.replay(replayButtons());
    }
}