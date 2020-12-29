package sdmExam;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> _plays = new ArrayList<>();

    public Board(){
        // Board is 13x13, but we can use edges and corners mark so it is 14x14
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 14; j++){
                Mark mark = Mark.NONE;
                Position position = new Position(i,j);
                System.out.println(position);
                Cell cell = new Cell(position,mark);
                _plays.add(cell);
            }
        }
    }

    public Cell cellAt(Position position){
        for (Cell c : _plays){
            if (position.getRow() == c.getPosition().getRow() && position.getColumn() == c.getPosition().getColumn()){
                return c;
            }
        }
        return null;
    }
}
