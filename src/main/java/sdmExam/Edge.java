package sdmExam;

public class Edge {
    private Stone color;
    private int position;

    public Edge(Stone color, int position) {
        this.color = color;
        this.position = position;
    }

    public boolean isAboveWithRespectTo(Position boardPosition) {
        return this.position == boardPosition.getRow() - 1;
    }

    public boolean isBelowWithRespectTo(Position boardPosition) {
        return this.position == boardPosition.getRow() + 1;
    }

    public boolean isOnTheLeftWithRespectTo(Position boardPosition) {
        return this.position == boardPosition.getColumn() - 1;
    }
}
