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
import quentin.UI.GUI.Handlers.*;
import quentin.GUIQuentin;

public class GUI extends Application {

    public static final int TILE_SIZE = 50;
    private final GameInterface gameInterface = new GameInterface(this);
    private Stage stage;
    private GridPane gridPane;
    private GUIQuentin guiQuentin;
    private GUIBoardDisplayer boardFiller;

    private GridPane getGridBoard() {
        GridPane borders = (GridPane) gridPane.getChildrenUnmodifiable().get(0);
        return (GridPane) borders.getChildren().get(0);
    }

    public Stage getStage() {return stage;}
    private GridPane getLabelBoard() { return (GridPane) gridPane.getChildren().get(1); }
    public GUIQuentin getGame() { return guiQuentin; }

    public void switchColorPlayerLabel() {
        boardFiller.switchColorPlayerLabel(getLabelBoard());
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setResizable(false);
        gameInterface.initUI();
    }

    private void addGridEvent(GridPane gridBoard) {
        gridBoard.addEventHandler(PieRuleEvent.PIE_RULE_EVENT_TYPE, new GuiPieHandler(this));
        gridBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, new GuiMouseHandler(this));
        gridBoard.addEventHandler(PassEvent.PASS_EVENT_TYPE, new GuiPassHandler(guiQuentin));
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

    protected void initGameInterface(int boardSize, String namePlayer1, String namePlayer2) {

        guiQuentin = new GUIQuentin(boardSize,new GUIInputHandler(), new GUIOutputHandler(), namePlayer1, namePlayer2);
        boardFiller = new GUIBoardDisplayer(boardSize, TILE_SIZE);

        GridPane gridBoard = createGridBoard();
        addGridEvent(gridBoard);

        Scene scene = new Scene(gridPane, Color.WHITESMOKE);

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
                        boardFiller.addPiece(getGridBoard(),
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