package sdmExam;

import java.util.Objects;

public class Intersection {
    private final Position position;
    private Mark mark;

    public Intersection(Position position, Mark mark) {
        this.position = position;
        this.mark = mark;
    }

    @Override
    public boolean equals(Object anotherCell) {
        if (this == anotherCell) return true;
        if (anotherCell == null || getClass() != anotherCell.getClass()) return false;
        Intersection intersection = (Intersection) anotherCell;
        return position.equals(intersection.position) && mark == intersection.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, mark);
    }

    public static Intersection empty(Position position){
        return new Intersection(position, Mark.NONE);
    }

    public Position getPosition() { return this.position; }
    public Mark getMark() { return this.mark; }
    public void setMark(Mark mark) { this.mark = mark; }

    public boolean isAt(Position position) {
        return position.equals(getPosition());
    }

    //TODO: implement a method that returns true if the color of the intersection matches the color of the adjacent edge
}
