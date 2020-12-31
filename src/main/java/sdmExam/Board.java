package sdmExam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private List<Cell> cells = new ArrayList<>();

    public Board() {
        // Board is 13x13 squares, but we can use edges and corners to mark so it is 14x14 cells
        //TODO: think about a way to model the borders the board
        for (int row = 0; row < 14; row++) {
            for (int column = 0; column < 14; column++) {
                cells.add(Cell.empty(Position.in(row, column)));
            }
        }
    }

    public Optional<Cell> cellAt(Position position) {
        return cells.stream().filter(cell -> cell.isAt(position)).findFirst();
    }

    public void addMarkAt(Mark mark, Position position) {
        //TODO: maybe we need to throw an exception if we try to modify and invalid cell;
        // we'll see when we will implement the actual game
        cellAt(position).ifPresent(cell -> cell.setMark(mark));
    }
}