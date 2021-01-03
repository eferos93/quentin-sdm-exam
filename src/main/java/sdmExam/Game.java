package sdmExam;

public class Game {
    private final Board board;
    private Mark player1 = Mark.BLACK;
    private Mark player2 = Mark.WHITE;
    private boolean firstPlay = true;
    private Mark lastPlayer;

    public Game(){this.board = new Board();}

    public void play(Mark player, Position position) throws Exception{
        if(firstPlay && player == this.player2){
            throw new Exception();
        }

        if(player == lastPlayer){
            throw new Exception();
        }

        lastPlayer = player;
    }
}
