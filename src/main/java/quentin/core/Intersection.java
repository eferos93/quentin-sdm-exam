package quentin.core;

import java.util.Objects;

public class Intersection {
    private final Position position;
    private Color color;

    protected Intersection(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public boolean equals(Object anotherCell) {
        if (this == anotherCell) return true;
        if (anotherCell == null || getClass() != anotherCell.getClass()) return false;
        Intersection intersection = (Intersection) anotherCell;
        return position.equals(intersection.position) && color == intersection.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, color);
    }

    public static Intersection empty(Position position) {
        return new Intersection(position, Color.NONE);
    }

    public final Position getPosition() {
        return this.position;
    }

    public final Color getStone() {
        return this.color;
    }

    protected void setStone(Color color) {
        this.color = color;
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

    public boolean hasStone(Color color) {
        return this.color == color;
    }

    public boolean isEmpty() { return hasStone(Color.NONE); }

    @Override
    public String toString() {
        return "Intersection{" +
                "position=" + position +
                ", stone=" + color +
                '}';
    }
}
