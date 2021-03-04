package quentin.core;

import java.util.Objects;

public class Position {
    private final int row, column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position in(int row, int column) {
        return new Position(row, column);
    }

    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition) return true;
        if (otherPosition == null || getClass() != otherPosition.getClass()) return false;
        Position position = (Position) otherPosition;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    boolean isAboveWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row + 1 &&
                otherIntersectionPosition.getColumn() == this.column;
    }

    boolean isBelowWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row - 1 &&
                otherIntersectionPosition.getColumn() == this.column;
    }

    boolean isOnTheLeftWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getColumn() == this.column + 1 &&
                otherIntersectionPosition.getRow() == this.row;
    }

    boolean isOnTheRightWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getColumn() == this.column - 1 &&
                otherIntersectionPosition.getRow() == this.row;
    }

    boolean isDownLeftRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row - 1 &&
                otherIntersectionPosition.getColumn() == this.column + 1;
    }

    boolean isUpLeftRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row + 1 &&
                otherIntersectionPosition.getColumn() == this.column + 1;
    }

    boolean isDownRightRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row - 1 &&
                otherIntersectionPosition.getColumn() == this.column - 1;
    }

    boolean isUpRightRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row + 1 &&
                otherIntersectionPosition.getColumn() == this.column - 1;
    }

    protected boolean isDiagonalTo(Position otherPosition) {
        return isUpLeftRespectTo(otherPosition) ||
                isUpRightRespectTo(otherPosition) ||
                isDownLeftRespectTo(otherPosition) ||
                isDownRightRespectTo(otherPosition);
    }

    protected boolean isOrthogonalTo(Position otherPosition) {
        return isBelowWithRespectTo(otherPosition) ||
                isAboveWithRespectTo(otherPosition) ||
                isOnTheLeftWithRespectTo(otherPosition) ||
                isOnTheRightWithRespectTo(otherPosition);
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
