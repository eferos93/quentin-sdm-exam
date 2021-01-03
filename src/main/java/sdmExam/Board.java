package sdmExam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {
    public static final int BOARD_SIZE = 14;
    private final List<Intersection> intersections = new ArrayList<>();
    private Mark lowerAndUpperEdgesColor = Mark.BLACK;
    private Mark leftAndRightEdgesColor = Mark.WHITE;

    public Board() {
        for (int row = 1; row < BOARD_SIZE; row++) {
            for (int column = 1; column < BOARD_SIZE; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }
    }

    public Intersection intersectionAt(Position position) throws NoSuchElementException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow();
    }

    public void addMarkAt(Mark mark, Position position) throws NoSuchElementException {
        intersectionAt(position).setMark(mark);
    }

    public void setLowerAndUpperEdgesColor(Mark mark) {
        this.lowerAndUpperEdgesColor = mark;
    }

    public void setLeftAndRightEdgesColor(Mark mark) {
        this.leftAndRightEdgesColor = mark;
    }

    public Mark getLowerAndUpperEdgesColor() {
        return lowerAndUpperEdgesColor;
    }

    public Mark getLeftAndRightEdgesColor() {
        return leftAndRightEdgesColor;
    }

    public boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }
}