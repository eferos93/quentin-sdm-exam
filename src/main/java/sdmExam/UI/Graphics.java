package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Game;
import sdmExam.Position;
import sdmExam.Stone;

public class Graphics {



    public static void printTitle() {System.out.println(Message.TITLE);}

    public static void Instructions() {System.out.println(Message.INSTRUCTIONS);}

    public static void BlackPlayerPlayFirst() {System.out.println(Message.BLACKPLAYFIRST);}

    public static void ChooseCoordinateX() {System.out.println(Message.CHOOSE_X);}

    public static void ChooseCoordinateY() {System.out.println(Message.CHOOSE_Y);}

    public static void SetCoordinates(Board board,int x, int y) {
        Position position = new Position(x, y);
        board.addStoneAt(Stone.BLACK, position);
    }

    public static void Pie() {System.out.println(Message.PIE);}

    public static void ApplyPie() {
        //We badly understood PieRule that must change
    }
}