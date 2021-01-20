package sdmExam;

public enum BoardSide {
    LEFT(Stone.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getSideIndex() == position.getColumn();
        }

        @Override
        protected void initialiseSide() {
            this.setSideIndex(1);
        }
    },
    RIGHT(Stone.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getColumn();
        }

        @Override
        protected void initialiseSide() {
            this.setSideIndex(BoardSide.boardSize);
        }
    },
    BOTTOM(Stone.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getRow();
        }

        @Override
        protected void initialiseSide() {
            this.setSideIndex(BoardSide.boardSize);
        }
    },
    TOP(Stone.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getRow();
        }

        @Override
        protected void initialiseSide() {
            this.setSideIndex(1);
        }
    };

    private final Stone color;
    private int sideIndex;
    private static int boardSize;

    BoardSide(Stone color) {
        this.color = color;
    }

    abstract protected void initialiseSide();

    abstract protected boolean isAdjacentTo(Position position);

    protected int getSideIndex() {
        return this.sideIndex;
    }

    protected void setSideIndex(int edgeIndex) {
        this.sideIndex = edgeIndex;
    }

    protected static void setBoardSize(int boardSize) {
        BoardSide.boardSize = boardSize;
    }

    protected boolean hasColor(Stone color) {
        return this.color == color;
    }

    @Override
    public String toString() {
        return "Side{" +
                "color=" + color +
                ", sideIndex=" + sideIndex +
                '}';
    }
}
