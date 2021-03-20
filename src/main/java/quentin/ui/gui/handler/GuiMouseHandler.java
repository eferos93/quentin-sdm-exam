package quentin.ui.gui.handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import quentin.GUIQuentin;
import quentin.ui.gui.events.EventFactory;
import quentin.ui.gui.GUI;
import quentin.core.Position;
import quentin.exceptions.QuentinException;

public class GuiMouseHandler implements EventHandler<MouseEvent> {

    private final GUI gui;

    public GuiMouseHandler(GUI gui) { this.gui = gui; }

    private int convertCoordinate(double coordinate) {
        return ((int)(coordinate - 1) / GUI.TILE_SIZE) + 1;
    }

    @Override
    public void handle(MouseEvent event) {
        int columnIndex = convertCoordinate(event.getX());
        int rowIndex = convertCoordinate(event.getY());
        GUIQuentin game = gui.getGame();

        game.setNewPosition(Position.in(rowIndex, columnIndex));

        if (game.isCurrentPlayerNotAbleToMakeAMove()) {
            gui.fireEvent(EventFactory.createPassEvent(game));
            return;
        }

        try {
            game.play();
        } catch (QuentinException exception) {
            gui.notifyException(exception);
            return;
        }

        gui.updateGUI();
        gui.fireEndGameEventIfConditionsAreMet();
        gui.firePieRuleIfConditionsAreMet();
        event.consume();
    }
}
