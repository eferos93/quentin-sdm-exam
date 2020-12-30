package sdmExam;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> _plays = new ArrayList<>();

    public Board(){
        // Board is 13x13 squares, but we can use edges and corners to mark so it is 14x14 cells
        for (int row = 0; row < 14; row++){
            for (int column = 0; column < 14; column++){
                Cell cell = Cell.addEmptyCell(Position.in(row,column));
                _plays.add(cell);
            }
        }
    }

    public Cell cellAt(Position position){
        for (Cell c : _plays){
            if (position.equals(c.getPosition())) return c;
        }
        return null;
    }

    public void AddMarkAt(Mark mark, Position position) {
        Cell newCell = cellAt(new Position(5, 7));
        newCell.setMark(Mark.BLACK);
    }
}
