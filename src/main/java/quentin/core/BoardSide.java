package quentin.core;

public enum BoardSide {
    LEFT(Colour.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return  getSideIndex() == position.getColumn();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(1);
        }
    },
    RIGHT(Colour.WHITE) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getColumn();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(BoardSide.boardSize);
        }
    },
    BOTTOM(Colour.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getRow();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(BoardSide.boardSize);
        }
    },
    TOP(Colour.BLACK) {
        @Override
        public boolean isAdjacentTo(Position position) {
            return getSideIndex() == position.getRow();
        }

        @Override
        public void initialiseSide() {
            this.setSideIndex(1);
        }
    };

    private final Colour colour;
    private int sideIndex;
    private static int boardSize;

    BoardSide(Colour colour) {
        this.colour = colour;
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

    protected boolean hasColor(Colour colour) {
        return this.colour == colour;
    }
}
