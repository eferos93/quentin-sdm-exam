package sdmExam;

public class Game {
    private final Board board;
    private Mark player1 = Mark.BLACK;
    private Mark player2 = Mark.WHITE;

    public Game(Board board){this.board = board;}

    public Board getBoard() { return board; }
}
