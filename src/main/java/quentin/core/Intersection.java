package quentin.core;

import java.util.Objects;

public class Intersection {
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

    protected void setStone(Stone stone) {
        this.stone = stone;
    }

    protected boolean isAt(Position position) {
        return position.equals(getPosition());
    }

    protected boolean isOccupied() {
        return !this.isEmpty();
    }

    protected boolean isOrthogonalTo(Intersection otherIntersection) {
        return position.isOrthogonalTo(otherIntersection.getPosition());
    }

    protected boolean isDiagonalTo(Intersection otherIntersection) {
        return position.isDiagonalTo(otherIntersection.getPosition());
    }

    public boolean hasStone(Stone stone) {
        return this.stone == stone;
    }

    public boolean isEmpty() { return hasStone(Stone.NONE); }

    @Override
    public String toString() {
        return "Intersection{" +
                "position=" + position +
                ", stone=" + stone +
                '}';
    }
}
