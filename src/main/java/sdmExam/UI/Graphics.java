package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Game;
import sdmExam.Position;
import sdmExam.Stone;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Graphics {
    private static int coordinateX;
    private static final Scanner scanner = new Scanner(System.in);

    public static void printTitle() {System.out.println(Message.TITLE);}

    public static void Instructions() {System.out.println(Message.INSTRUCTIONS);}

    public static void BlackPlayerPlayFirst() {System.out.println(Message.BLACKPLAYFIRST);}
    public static void FillEdges(Board board){
        IntStream.range(2,15).forEach(y-> board.addStoneAt(Stone.WHITE,new Position(1,y)));
        IntStream.range(2,15).forEach(y-> board.addStoneAt(Stone.WHITE,new Position(15,y)));
        IntStream.range(2,15).forEach(x-> board.addStoneAt(Stone.BLACK,new Position(x,1)));
        IntStream.range(2,15).forEach(x-> board.addStoneAt(Stone.BLACK,new Position(x,15)));
        PrintBoard.Print(board);

    }
    public static void Set(Stone stone, Board board, Game game) throws  Exception  {
        System.out.println(Message.CHOOSE_X);
        coordinateX=scanner.nextInt();
        System.out.println(Message.CHOOSE_Y);
        game.play(stone,new Position(coordinateX,scanner.nextInt()));
        board.addStoneAt(stone,new Position(coordinateX,scanner.nextInt()));
        PrintBoard.Print(board);
    }

    public static void Pie() {System.out.println(Message.PIE);}

    public static void ApplyPie(Board board) { board.pie();}

    public static boolean SomeoneWin() {return true;}
    //Waiting for chain
}