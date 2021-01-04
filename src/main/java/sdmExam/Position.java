package sdmExam;

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
    public boolean equals(Object anotherPosition) {
        if (this == anotherPosition) return true;
        if (anotherPosition == null || getClass() != anotherPosition.getClass()) return false;
        Position position = (Position) anotherPosition;
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

    public boolean isBelowWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row + 1 &&
                otherIntersectionPosition.getColumn() == this.column;
    }

    public boolean isAboveWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getRow() == this.row - 1 &&
                otherIntersectionPosition.getColumn() == this.column;
    }

    public boolean isOnTheLeftWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getColumn() == this.column + 1 &&
                otherIntersectionPosition.getRow() == this.row;
    }

    public boolean isOnTheRightWithRespectTo(Position otherIntersectionPosition) {
        return otherIntersectionPosition.getColumn() == this.column - 1 &&
                otherIntersectionPosition.getRow() == this.row;
    }
}
