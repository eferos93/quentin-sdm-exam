package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import quentin.UI.GUI.Events.EventFactory;
import quentin.UI.GUI.GUI;
import quentin.core.Position;

public class GuiMouseHandler implements EventHandler<MouseEvent> {

    private final GUI gui;

    public GuiMouseHandler(GUI gui) { this.gui = gui; }

    @Override
    public void handle(MouseEvent event) {
        int columnIndex = gui.coordinateConversion(event.getX());
        int rowIndex = gui.coordinateConversion(event.getY());

        gui.getGame().setNewPosition(Position.in(rowIndex + 1, columnIndex + 1));

        if (Boolean.TRUE.equals(!gui.getGame().checkNewMove())) {
            System.out.println("INVALID MOVE");
            return;
        }

        updateGUIAndFireEvents(columnIndex, rowIndex);
        event.consume();
    }

    private void updateGUIAndFireEvents (int columnIndex, int rowIndex) {
        gui.getBoardFiller().addPiece(gui.getGridBoard(), columnIndex, rowIndex, gui.getGame().getCurrentPlayer().getColor());
        gui.getGame().play();

        gui.fillGridBoardWithTerritories();
        gui.getGame().fillTerritories();

        if(Boolean.TRUE.equals(gui.getGame().getPlayEndSuccessfully())) {
            gui.getBoardFiller().switchLabelsCurrentPlayer(gui.getLabelBoard());
        }

        EventFactory.create().forEach(x -> gui.getGridBoard().fireEvent(x));
    }

}
