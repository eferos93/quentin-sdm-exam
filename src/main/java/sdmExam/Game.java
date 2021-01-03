package sdmExam;

public class Game {
    private final Board board;
    private Mark player1 = Mark.BLACK;
    private Mark player2 = Mark.WHITE;

    public Game(){this.board = new Board();}

    public void play(Mark player, Position position) throws Exception{
        throw new Exception();
    }
}
