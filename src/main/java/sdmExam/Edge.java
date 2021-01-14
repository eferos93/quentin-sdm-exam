package sdmExam;

public enum Edge {
    LEFT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getEdgeIndex() == position.getColumn();
        }
    },
    RIGHT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getColumn();
        }
    },
    BOTTOM {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
        }
    },
    TOP {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow();
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

    protected void initialiseEdge(int edgeIndex) {
        switch (this) {
            case TOP -> {this.setColor(Stone.BLACK); this.setEdgeIndex(1);}
            case BOTTOM -> {this.setColor(Stone.BLACK); this.setEdgeIndex(edgeIndex);}
            case LEFT -> {this.setColor(Stone.WHITE); this.setEdgeIndex(1);}
            case RIGHT -> {this.setColor(Stone.WHITE); this.setEdgeIndex(edgeIndex);}
        }
    }

    abstract public boolean isAdjacentTo(Position position);
}
