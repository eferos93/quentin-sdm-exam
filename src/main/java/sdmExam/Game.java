package sdmExam;

public class Game {
    private final Board board = new Board();
    private Stone lastPlay = Stone.NONE;

    public void play(Stone player, Position position) throws Exception {

        if (isInvalidFirstPlayer(player)) {
            throw new Exception("Black player should play first.");
        }

        if (lastPlay == player) {
            throw new Exception("A player cannot play twice in a row.");
        }

        if (board.isOccupied(position)) {
            throw new Exception("Position is already occupied.");
        }

        board.addStoneAt(player, position);
        lastPlay = player;
    }

    private boolean isInvalidFirstPlayer(Stone player) {
        return lastPlay == Stone.NONE && player == Stone.WHITE;
    }
}
