package sdmExam;

import java.security.spec.ECField;

public class Game {
    private final Board board;
    private Mark player1 = Mark.BLACK;
    private Mark player2 = Mark.WHITE;
    private Mark lastPlayer = Mark.WHITE;

    public Game(){this.board = new Board();}

    public void play(Mark player, Position position) throws Exception{

        if(isInvalidFirstPlayer(player)){
            throw new Exception("Black player should play first.");
        }

        if(board.isMarked(position)){
            throw new Exception();
        }

        board.addMarkAt(player, position);
        lastPlayer = player;
    }

    private boolean isInvalidFirstPlayer(Mark player) {
        return player == lastPlayer;
    }
}
