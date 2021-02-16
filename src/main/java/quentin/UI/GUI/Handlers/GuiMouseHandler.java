package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import quentin.GUIQuentin;
import quentin.UI.GUI.Events.EventFactory;
import quentin.UI.GUI.GUI;
import quentin.UI.GUI.GUIOutputHandler;
import quentin.core.Player;
import quentin.core.Position;

public class GuiMouseHandler implements EventHandler<MouseEvent> {

    private final GUI gui;

    public GuiMouseHandler(GUI gui) { this.gui = gui; }

    @Override
    public void handle(MouseEvent event) {
        int columnIndex = gui.coordinateConversion(event.getX());
        int rowIndex = gui.coordinateConversion(event.getY());

        gui.getGame().setNewPosition(Position.in(rowIndex + 1, columnIndex + 1));

//        if (!gui.getGame().checkNewMove()) {
//            System.out.println("INVALID MOVE");
//            return;
//        }
        GUIQuentin game = gui.getGame();
        Player currentPlayer = game.getCurrentPlayer();
        if (!game.isPlayerAbleToMakeAMove(currentPlayer)) {
            game.passTurn(currentPlayer);
            return;
        }

        try {
            game.play();
        } catch (Exception exception) {
            GUIOutputHandler.notifyException(exception.toString());
            return;
        }

        updateGUIAndFireEvents(columnIndex, rowIndex, currentPlayer);
        event.consume();
    }

    // this method should be executed only if the move is valid
    private void updateGUIAndFireEvents (int columnIndex, int rowIndex, Player currentPlayer) {
        gui.getBoardFiller().addPiece(gui.getGridBoard(),
                columnIndex, rowIndex, currentPlayer.getColor());
        //gui.getGame().play();

        gui.fillGridBoardWithTerritories();
        gui.getGame().fillTerritories();


       // if (gui.getGame().getPlayEndSuccessfully()) {
            gui.getBoardFiller().switchLabelsCurrentPlayer(gui.getLabelBoard());
        //}

        EventFactory.create().forEach(event -> gui.getGridBoard().fireEvent(event));
    }

}
