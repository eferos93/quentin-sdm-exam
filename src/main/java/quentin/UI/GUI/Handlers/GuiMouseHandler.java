package quentin.UI.GUI.Handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import quentin.UI.GUI.EventFactory;
import quentin.UI.GUI.GUI;
import quentin.core.Intersection;
import quentin.core.Position;

public class GuiMouseHandler implements EventHandler<MouseEvent> {

    private final GUI gui;

    public GuiMouseHandler(GUI g) { gui = g; }

    @Override
    public void handle(MouseEvent event) {
        int columnIndex = gui.coordinateConversion(event.getX());
        int rowIndex = gui.coordinateConversion(event.getY());

        gui.getGame().setNewPosition(Position.in(rowIndex + 1, columnIndex + 1));

        if (Boolean.TRUE.equals(!gui.getGame().checkAndPerformNewMove())) {
            System.out.println("INVALID MOVE: something goes wrong");
            return;
        }

        updateGUIAndFireEvents(columnIndex, rowIndex);
        event.consume();
    }

    private void updateGUIAndFireEvents (int columnIndex, int rowIndex) {
        gui.getBoardFiller().addPiece(gui.getGridBoard(), columnIndex, rowIndex, gui.getGame().getCurrentPlayer().getColor());
        gui.getGame().play(); // update Board.board if it is possible

        fillGridBoardWithTerritories(); // fill gui board representation with territories if any
        gui.getGame().fillTerritories(); // fill Board.board with territories if any

        if(Boolean.TRUE.equals(gui.getGame().getPlayEndSuccessfully())) {
            gui.getBoardFiller().switchLabelsCurrentPlayer(gui.getLabelBoard());
        }

        EventFactory.create().forEach(x -> gui.getGridBoard().fireEvent(x));
    }

    private void fillGridBoardWithTerritories(){
        gui.getGame().getTerritoriesAndStones(gui.getGame().getLastPlay()).forEach((territory, stone) ->
                territory.stream().map(Intersection::getPosition).
                        forEach(position -> gui.getBoardFiller().
                                addPiece(gui.getGridBoard(),
                                        position.getColumn() - 1, // to be consistent with the board gui representation
                                        position.getRow() - 1, // to be consistent with the board gui representation
                                        stone)));
    }
}
