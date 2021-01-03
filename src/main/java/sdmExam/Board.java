package sdmExam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private final List<Intersection> intersections = new ArrayList<>();
    private Mark lowerAndUpperEdgesColor = Mark.BLACK;
    private Mark leftAndRightEdgesColor = Mark.WHITE;

    public Board() {
        for (int row = 1; row < 14; row++) {
            for (int column = 1; column < 14; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }
    }

    public Optional<Intersection> intersectionAt(Position position) {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst();
    }

    public void addMarkAt(Mark mark, Position position) {
        //TODO: maybe we need to throw an exception if we try to modify and invalid cell;
        // we'll see when we will implement the actual game
        intersectionAt(position).ifPresent(intersection -> intersection.setMark(mark));
    }

    public void setLowerAndUpperEdgesColor(Mark mark) {
        this.lowerAndUpperEdgesColor = mark;
    }
    public void setLeftAndRightEdgesColor(Mark mark) { this.leftAndRightEdgesColor = mark; }
    public Mark getLowerAndUpperEdgesColor() {
        return lowerAndUpperEdgesColor;
    }
    public Mark getLeftAndRightEdgesColor() {
        return leftAndRightEdgesColor;
    }
}