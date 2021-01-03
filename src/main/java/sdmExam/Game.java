package sdmExam;

public class Game {
    private final Board board = new Board();
    private Mark lastPlayer = Mark.WHITE;

    public void play(Mark player, Position position) throws Exception {

        if (isInvalidFirstPlayer(player)) {
            throw new Exception("Black player should play first.");
        }

        if (board.isMarked(position)) {
            throw new Exception("Position is already occupied.");
        }

        board.addMarkAt(player, position);
        lastPlayer = player;
    }

    private boolean isInvalidFirstPlayer(Mark player) {
        return player == lastPlayer;
    }
}
