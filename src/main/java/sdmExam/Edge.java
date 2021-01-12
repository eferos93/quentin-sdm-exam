package sdmExam;

public enum Edge {
    LEFT {
        @Override
        public boolean isAdjacentTo(Position position, int edgePosition) {
            return  edgePosition == position.getColumn() - 1;
        }
    },
    RIGHT {
        @Override
        public boolean isAdjacentTo(Position position, int edgePosition) {
            return edgePosition == position.getColumn() + 1;
        }
    },
    DOWN {
        @Override
        public boolean isAdjacentTo(Position position, int edgePosition) {
            return edgePosition == position.getRow() + 1;
        }
    },
    UP {
        @Override
        public boolean isAdjacentTo(Position position, int edgePosition) {
            return edgePosition == position.getRow() - 1;
        }

    };

    private Stone color;


    protected void setColor(Stone color) {
        this.color = color;
    }

    protected boolean hasColor(Stone color) {
        return this.color == color;
    }

    abstract public boolean isAdjacentTo(Position position, int edgePosition);
}
