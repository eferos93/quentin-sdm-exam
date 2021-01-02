package sdmExam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private List<Intersection> intersections = new ArrayList<>();
    private Mark lowerAndUpperEdges = Mark.BLACK;
    private Mark leftAndRightEdges = Mark.WHITE;

    public Board() {
        // Board is 13x13 squares, but we can use edges and corners to mark so it is 14x14 cells
        //TODO: think about a way to model the borders the board
        for (int row = 1; row < 14; row++) {
            for (int column = 1; column < 14; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }
    }

    public Optional<Intersection> cellAt(Position position) {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst();
    }

    public void addMarkAt(Mark mark, Position position) {
        //TODO: maybe we need to throw an exception if we try to modify and invalid cell;
        // we'll see when we will implement the actual game
        cellAt(position).ifPresent(intersection -> intersection.setMark(mark));
    }
}