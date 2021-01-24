package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Game;
import sdmExam.Position;
import sdmExam.Stone;

public class Graphics {

    static InputHandler inputHandler = new InputHandler();

    public static void printTitle() {System.out.println(Message.TITLE);}

    public static void Instructions() {System.out.println(Message.INSTRUCTIONS);}

    public static void BlackPlayerPlayFirst() {System.out.println(Message.BLACK_PLAYS_FIRST);}

    public static void Pie() {System.out.println(Message.PIE);}

    public static void ApplyPie(Board board) { board.pie();}

    public static void Set(Stone stone, Board board, Game game) throws  Exception  {
        System.out.println(Message.CHOOSE_ROW);
        int coordinateX = InputHandler.getInteger();
        System.out.println(Message.CHOOSE_COLUMN);
        int coordinateY = InputHandler.getInteger() ;
        game.play(stone,new Position(coordinateX,coordinateY));
        board.addStoneAt(stone,new Position(coordinateX,coordinateY));
        PrintBoard.Print(board);
    }

}