package sdmExam.core;

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

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public boolean isAt(Position position) {
        return position.equals(getPosition());
    }

    public boolean isOccupied() {
        return !hasStone(Stone.NONE);
    }

    public boolean isOrthogonalTo(Intersection otherIntersection) {
        final Position otherIntersectionPosition = otherIntersection.getPosition();
        return position.isBelowWithRespectTo(otherIntersectionPosition) ||
                position.isAboveWithRespectTo(otherIntersectionPosition) ||
                position.isOnTheLeftWithRespectTo(otherIntersectionPosition) ||
                position.isOnTheRightWithRespectTo(otherIntersectionPosition);
    }

    public boolean isDiagonalTo(Intersection otherIntersection) {
        final Position otherIntersectionPosition = otherIntersection.getPosition();
        return position.isUpLeftRespectTo(otherIntersectionPosition) ||
                position.isUpRightRespectTo(otherIntersectionPosition) ||
                position.isDownLeftRespectTo(otherIntersectionPosition) ||
                position.isDownRightRespectTo(otherIntersectionPosition);
    }

    public boolean hasStone(Stone stone) {
        return this.stone == stone;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "position=" + position +
                ", stone=" + stone +
                '}';
    }

}
