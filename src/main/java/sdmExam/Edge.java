package sdmExam;

public enum Edge {
    LEFT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getEdgeIndex() == position.getColumn() - 1;
        }
    },
    RIGHT {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getColumn() + 1;
        }
    },
    BOTTOM {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow() + 1;
        }
    },
    TOP {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getEdgeIndex() == position.getRow() - 1;
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
            case TOP -> {this.setColor(Stone.BLACK); this.setEdgeIndex(0);}
            case BOTTOM -> {this.setColor(Stone.BLACK); this.setEdgeIndex(edgeIndex);}
            case LEFT -> {this.setColor(Stone.WHITE); this.setEdgeIndex(0);}
            case RIGHT -> {this.setColor(Stone.WHITE); this.setEdgeIndex(edgeIndex);}
        }
    }

    abstract public boolean isAdjacentTo(Position position);

    @Override
    public String toString() {
        return "Edge{" +
                "color=" + color +
                ", edgeIndex=" + edgeIndex +
                '}';
    }
}
