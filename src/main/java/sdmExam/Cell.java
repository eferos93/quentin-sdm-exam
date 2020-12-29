package sdmExam;

import java.util.Objects;

public class Cell {
    private Position position;
    private Mark mark;

    public Cell(Position position, Mark mark) {
        this.position = position;
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return position.equals(cell.position) && mark == cell.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, mark);
    }
}
