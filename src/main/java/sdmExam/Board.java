package sdmExam;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> cells = new ArrayList<>();

    public Board(){
        // Board is 13x13 squares, but we can use edges and corners to mark so it is 14x14 cells
        for (int row = 0; row < 14; row++){
            for (int column = 0; column < 14; column++){
                cells.add(Cell.empty(Position.in(row, column)));
            }
        }
    }

    public Cell cellAt(Position position){
        for (Cell c : cells){
            if (isAt(position, c)) return c;
        }
        return null;
    }

    private boolean isAt(Position position, Cell c) {
        return position.equals(c.getPosition());
    }

    public void AddMarkAt(Mark mark, Position position) {
        Cell cell = cellAt(position);
            if (cell != null && cell.getMark() == Mark.NONE) {
                cell.setMark(mark);}
            else{System.out.print("You marked illegal cell");}
         }
}