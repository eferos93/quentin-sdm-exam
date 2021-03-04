package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import quentin.GUIQuentin;
import quentin.UI.GUI.GUI;
import quentin.core.Player;
import quentin.core.Position;
import quentin.exceptions.QuentinException;

public class GuiMouseHandler implements EventHandler<MouseEvent> {

    private final GUI gui;

    public GuiMouseHandler(GUI gui) { this.gui = gui; }

    private int convertCoordinate(double coordinate) {
        return (int)(coordinate - 1) / GUI.TILE_SIZE;
    }

    @Override
    public void handle(MouseEvent event) {
        int columnIndex = convertCoordinate(event.getX());
        int rowIndex = convertCoordinate(event.getY());
        GUIQuentin game = gui.getGame();

        game.setNewPosition(Position.in(rowIndex + 1, columnIndex + 1));

        Player currentPlayer = game.getCurrentPlayer();
        if (game.isCurrentPlayerNotAbleToMakeAMove()) {
            game.passTurn();
            return;
        }

        try {
            game.play();
        } catch (QuentinException exception) {
            gui.notifyException(exception);
            return;
        }

        gui.updateGUI(columnIndex, rowIndex, currentPlayer);
        gui.fireEvents();
        event.consume();
    }
}
