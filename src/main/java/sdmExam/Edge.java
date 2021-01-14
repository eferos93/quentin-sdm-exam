package sdmExam;

public enum Edge {
    LEFT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getEdgeIndex() == position.getColumn();
        }
        @Override
        protected void initialiseEdge() {
            this.setColor(Stone.WHITE); this.setEdgeIndex(1);
        }
    },
    RIGHT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getColumn();
        }
        @Override
        protected void initialiseEdge() {
            this.setColor(Stone.WHITE); this.setEdgeIndex(Edge.boardSize);
        }
    },
    BOTTOM {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }
        @Override
        protected void initialiseEdge() {
            this.setColor(Stone.BLACK); this.setEdgeIndex(Edge.boardSize);
        }
    },
    TOP {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }
        @Override
        protected void initialiseEdge() {
            this.setColor(Stone.BLACK); this.setEdgeIndex(1);
        }
    };

    private Stone color;
    private int edgeIndex;
    private static int boardSize;

    abstract protected void initialiseEdge();

    abstract public boolean isAdjacentTo(Position position);

    protected int getEdgeIndex() {
        return edgeIndex;
    }

    protected void setEdgeIndex(int edgeIndex) {
        this.edgeIndex = edgeIndex;
    }

    protected static void setBoardSize(int boardSize) {
        Edge.boardSize = boardSize;
    }

    protected void setColor(Stone color) {
        this.color = color;
    }

    protected boolean hasColor(Stone color) {
        return this.color == color;
    }
}
