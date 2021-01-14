package sdmExam;

public enum Edge {
    LEFT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getEdgeIndex() == position.getColumn();
        }
        @Override
        protected void initialiseEdge(int edgeIndex) {
            this.setColor(Stone.WHITE); this.setEdgeIndex(1);
        }
    },
    RIGHT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getColumn();
        }
        @Override
        protected void initialiseEdge(int edgeIndex) {
            this.setColor(Stone.WHITE); this.setEdgeIndex(edgeIndex);
        }
    },
    BOTTOM {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }
        @Override
        protected void initialiseEdge(int edgeIndex) {
            this.setColor(Stone.BLACK); this.setEdgeIndex(edgeIndex);
        }
    },
    TOP {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }
        @Override
        protected void initialiseEdge(int edgeIndex) {
            this.setColor(Stone.BLACK); this.setEdgeIndex(1);
        }
    };

    private Stone color;
    private int edgeIndex;

    protected int getEdgeIndex() {
        return this.edgeIndex;
    }

    protected void setEdgeIndex(int edgeIndex) {
        this.edgeIndex = edgeIndex;
    }

    protected void setColor(Stone color) {
        this.color = color;
    }

    protected boolean hasColor(Stone color) {
        return this.color == color;
    }

    abstract protected void initialiseEdge(int edgeIndex);

    abstract public boolean isAdjacentTo(Position position);
}
