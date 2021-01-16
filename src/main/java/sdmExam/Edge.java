package sdmExam;

public enum Edge {
    LEFT(Stone.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getEdgeIndex() == position.getColumn();
        }

        @Override
        protected void initialiseEdge() {
            this.setEdgeIndex(1);
        }
    },
    RIGHT(Stone.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getColumn();
        }

        @Override
        protected void initialiseEdge() {
            this.setEdgeIndex(Edge.boardSize);
        }
    },
    BOTTOM(Stone.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }

        @Override
        protected void initialiseEdge() {
            this.setEdgeIndex(Edge.boardSize);
        }
    },
    TOP(Stone.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }

        @Override
        protected void initialiseEdge() {
            this.setEdgeIndex(1);
        }
    };

    private final Stone color;
    private int edgeIndex;
    private static int boardSize;

    Edge(Stone color) {
        this.color = color;
    }

    abstract protected void initialiseEdge();

    abstract protected boolean isAdjacentTo(Position position);

    protected int getEdgeIndex() {
        return this.edgeIndex;
    }

    protected void setEdgeIndex(int edgeIndex) {
        this.edgeIndex = edgeIndex;
    }

    protected static void setBoardSize(int boardSize) {
        Edge.boardSize = boardSize;
    }

    protected boolean hasColor(Stone color) {
        return this.color == color;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "color=" + color +
                ", edgeIndex=" + edgeIndex +
                '}';
    }
}
