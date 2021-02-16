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

        gui.updateGUIAndFireEvents(columnIndex, rowIndex, currentPlayer);
        event.consume();
    }
}
