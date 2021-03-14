package quentin.core;

public enum BoardSide {
    LEFT(Color.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getSideIndex() == position.getColumn();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(1);
        }
    },
    RIGHT(Color.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getColumn();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(BoardSide.boardSize);
        }
    },
    BOTTOM(Color.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getRow();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(BoardSide.boardSize);
        }
    },
    TOP(Color.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getRow();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(1);
        }
    };

    private final Color color;
    private int sideIndex;
    private static int boardSize;

    BoardSide(Color color) {
        this.color = color;
    }

    protected abstract void initialiseSide();

    protected abstract boolean isAdjacentTo(Position position);

    protected int getSideIndex() {
        return this.sideIndex;
    }

    protected void setSideIndex(int edgeIndex) {
        this.sideIndex = edgeIndex;
    }

    protected static void setBoardSize(int boardSize) {
        BoardSide.boardSize = boardSize;
    }

    protected boolean hasColor(Color color) {
        return this.color == color;
    }
}
