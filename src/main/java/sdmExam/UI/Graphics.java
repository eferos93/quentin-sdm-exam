package sdmExam.UI;

import sdmExam.Board;
import sdmExam.Position;

public class Graphics {
    private static int i,j;
    Board board = new Board();

    public static void printTitle() { System.out.println(Message.TITLE); }
    public static void Instructions() { System.out.println(Message.INSTRUCTIONS);}
    public static void BlackPlayerPlayFirst() {System.out.println(Message.BLACKPLAYFIRST);}
    public static void ChooseCoordinateX() { System.out.println(Message.CHOOSE_X);}
    public static void ChooseCoordinateY() { System.out.println(Message.CHOOSE_Y);}
    public static void SetCoordinates(int x, int y) {
        Position position=new Position(x,y);
    }


    public static void PrintBoard() {
        for( i = 0; i < 13; i++){
            System.out.print("\n");
            for( j = 0; j < 13; j++)
                System.out.print("[]");
        }

    }
}
