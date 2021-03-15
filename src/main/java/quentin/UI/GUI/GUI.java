package quentin.UI.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import quentin.UI.GUI.Events.*;
import quentin.GUIQuentin;
import quentin.UI.GUI.Handlers.GuiEndGameHandler;
import quentin.UI.GUI.Handlers.GuiMouseHandler;
import quentin.UI.GUI.Handlers.GuiPassHandler;
import quentin.UI.GUI.Handlers.GuiPieHandler;

public class GUI extends Application {

    public static final int TILE_SIZE = 50;
    private final GameInterface gameInterface = new GameInterface(this);
    private final BoardGridHandler boardGridHandler = new BoardGridHandler();
    private Stage stage;
    private GUIQuentin guiQuentin;

    public GUIQuentin getGuiQuentin() {
        return guiQuentin;
    }

    public Stage getStage() {return stage;}

    public void switchColorPlayerLabel() {
        boardGridHandler.switchColorPlayerLabel();
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setResizable(false);
        gameInterface.initUI();
    }

    protected void initGameInterface(int boardSize, String namePlayer1, String namePlayer2) {

        guiQuentin = new GUIQuentin(boardSize,new GUIInputHandler(), new GUIOutputHandler(), namePlayer1, namePlayer2);
        boardGridHandler.setBoardFiller(new GUIBoardDisplayer(boardSize, TILE_SIZE));

        GridPane gridBoard = boardGridHandler.createGridBoard(guiQuentin);
        addGridEvent(gridBoard);

        Scene scene = new Scene(boardGridHandler.getGridPane(), Color.WHITESMOKE);

        String path = getClass().getResource("/GUI.css").toExternalForm();
        scene.getStylesheets().add(path);

        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();
    }

    public void endUI() {
        gameInterface.endUI();
    }

    @Override
    public void stop() { Platform.exit(); }

    public void updateGUI() {
        guiQuentin.getNonEmptyIntersections()
                .forEach(nonEmptyIntersection ->
                        boardGridHandler.getBoardFiller().addPiece(boardGridHandler.getGridBoard(),
                                nonEmptyIntersection.getPosition(),
                                nonEmptyIntersection.getColor().orElseThrow()
                        )
                );
        boardGridHandler.getBoardFiller().switchLabelsCurrentPlayer(boardGridHandler.getLabelBoard());
    }

    private void addGridEvent(GridPane gridBoard) {
        gridBoard.addEventHandler(PieRuleEvent.PIE_RULE_EVENT_TYPE, new GuiPieHandler(this));
        gridBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, new GuiMouseHandler(this));
        gridBoard.addEventHandler(PassEvent.PASS_EVENT_TYPE, new GuiPassHandler(getGuiQuentin()));
        gridBoard.addEventHandler(EndGameEvent.END_GAME_EVENT_TYPE, new GuiEndGameHandler(this));
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
        boardGridHandler.getGridBoard().fireEvent(event);
    }
}