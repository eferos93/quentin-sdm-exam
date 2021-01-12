package sdmExam;

public enum Edge {
    LEFT(Stone.WHITE, 0) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getPosition() == position.getColumn() - 1;
        }
    },
    RIGHT(Stone.WHITE, 14) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getPosition() == position.getColumn() + 1;
        }
    },
    DOWN(Stone.BLACK, 14) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getPosition() == position.getRow() + 1;
        }
    },
    UP(Stone.BLACK, 0) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getPosition() == position.getRow() - 1;
        }
    };

    private Stone color;
    private int position;

    Edge(Stone color, int position) {
        this.color = color;
        this.position = position;
    }

    protected Stone getEdgeColor() {
        return color;
    }
    protected int getPosition() {
        return position;
    }

    protected void setColor(Stone color) {
        this.color = color;
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    abstract public boolean isAdjacentTo(Position position);
}
