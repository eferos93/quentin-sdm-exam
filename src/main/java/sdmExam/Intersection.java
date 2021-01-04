package sdmExam;

import java.util.Objects;

public class Intersection {
    public static final int UPPER_AND_LEFT_EDGE_INDEX = 1;
    public static final int LOWER_AND_RIGHT_EDGE_INDEX = 13;
    private final Position position;
    private Stone stone;

    public Intersection(Position position, Stone stone) {
        this.position = position;
        this.stone = stone;
    }

    @Override
    public boolean equals(Object anotherCell) {
        if (this == anotherCell) return true;
        if (anotherCell == null || getClass() != anotherCell.getClass()) return false;
        Intersection intersection = (Intersection) anotherCell;
        return position.equals(intersection.position) && stone == intersection.stone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, stone);
    }

    public static Intersection empty(Position position) {
        return new Intersection(position, Stone.NONE);
    }

    public Position getPosition() {
        return this.position;
    }

    public Stone getStone() {
        return this.stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public boolean isAt(Position position) {
        return position.equals(getPosition());
    }

    public boolean isCloseToEdge() {
        return isCloseToUpperOrLowerEdge() || isCloseToRightOrLeftEdge();
    }

    public boolean isCloseToRightOrLeftEdge() {
        return position.getColumn() == UPPER_AND_LEFT_EDGE_INDEX || position.getColumn() == LOWER_AND_RIGHT_EDGE_INDEX;
    }

    public boolean isCloseToUpperOrLowerEdge() {
        return position.getRow() == UPPER_AND_LEFT_EDGE_INDEX || position.getRow() == LOWER_AND_RIGHT_EDGE_INDEX;
    }

    public boolean isOccupied() {
        return stone != Stone.NONE;
    }
}
