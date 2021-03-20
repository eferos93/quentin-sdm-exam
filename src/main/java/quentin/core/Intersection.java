package quentin.core;

import java.util.Objects;
import java.util.Optional;

public class Intersection {
    private final Position position;
    private Colour colour;

    protected Intersection(Position position, Colour colour) {
        this.position = position;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object anotherCell) {
        if (this == anotherCell) return true;
        if (anotherCell == null || getClass() != anotherCell.getClass()) return false;
        Intersection intersection = (Intersection) anotherCell;
        return position.equals(intersection.position) && colour == intersection.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, colour);
    }

    public static Intersection empty(Position position) {
        return new Intersection(position, null);
    }

    public final Position getPosition() {
        return this.position;
    }

    public final Optional<Colour> getColour() {
        return Optional.ofNullable(this.colour);
    }

    protected void setStone(Colour colour) {
        this.colour = colour;
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

    public boolean hasStone(Colour colour) {
        return Optional.ofNullable(this.colour).map(intersectionColor -> intersectionColor == colour).orElse(false);
    }

    public boolean isEmpty() { return Optional.ofNullable(this.colour).isEmpty(); }

    @Override
    public String toString() {
        return "Intersection{" +
                "position=" + position +
                ", stone=" + colour +
                '}';
    }
}
