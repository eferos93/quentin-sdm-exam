package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Game;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.Scanner;

public class Graphics {
    private static int coordinateX;
    private static final Scanner scanner = new Scanner(System.in);

    public static void printTitle() {System.out.println(Message.TITLE);}

    public static void Instructions() {System.out.println(Message.INSTRUCTIONS);}

    public static void BlackPlayerPlayFirst() {System.out.println(Message.BLACKPLAYFIRST);}
    public static void Set(Stone stone, Board board, Game game) throws  Exception  {
        System.out.println(Message.CHOOSE_X);
        coordinateX=scanner.nextInt();
        System.out.println(Message.CHOOSE_Y);
        game.play(stone,new Position(coordinateX,scanner.nextInt()));
        board.addStoneAt(stone,new Position(coordinateX,scanner.nextInt()));
        PrintBoard.Print(board);
    }

    public static void Pie() {System.out.println(Message.PIE);}

    public static void ApplyPie() {
        //We badly understood PieRule that must change
    }
}