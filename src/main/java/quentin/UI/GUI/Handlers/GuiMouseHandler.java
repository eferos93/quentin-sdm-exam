package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import quentin.UI.GUI.EventFactory;
import quentin.UI.GUI.GUI;
import quentin.core.Position;

public class GuiMouseHandler implements EventHandler<MouseEvent> {

    private final GUI gui;

    public GuiMouseHandler(GUI g) { gui = g; }

    @Override
    public void handle(MouseEvent event) {
        int columnIndex = gui.coordinateConversion(event.getX());
        int rowIndex = gui.coordinateConversion(event.getY());

        gui.getGame().newPosition = Position.in(rowIndex + 1, columnIndex + 1);

        if (Boolean.TRUE.equals(!gui.getGame().checkAndPerformNewMove(gui.getGame().newPosition))) {
            System.out.println("something goes wrong");
            return;
        }

        updateGUIAndFireEvents(columnIndex, rowIndex);
        event.consume();
    }

    private void updateGUIAndFireEvents (int columnIndex, int rowIndex) {
        gui.getBoardFiller().addPiece(gui.getGridBoard(), columnIndex, rowIndex, gui.getGame().getCurrentPlayer().getColor());
        gui.getGame().play();

        if(gui.getGame().playTerminate) {
            gui.getBoardFiller().switchLabelsCurrentPlayer(gui.getLabelBoard());
        }
        EventFactory.create().forEach(x -> gui.getGridBoard().fireEvent(x));
    }
}
