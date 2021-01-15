package sdmExam;

import sdmExam.UI.Graphics;
import sdmExam.UI.PrintBoard;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int coordinateX;

        Game game = new Game();
        Board board = new Board();

        Graphics.printTitle();
        Graphics.Instructions();
        Graphics.BlackPlayerPlayFirst();
        PrintBoard.Print(board);
        Graphics.ChooseCoordinateX();
        coordinateX=scanner.nextInt();
        Graphics.ChooseCoordinateY();
        Graphics.SetCoordinates(board,coordinateX,scanner.nextInt());
        Graphics.Pie();
        if(scanner.nextInt()==1)
            Graphics.ApplyPie();
        else{
            Graphics.ChooseCoordinateX();
            coordinateX=scanner.nextInt();
            Graphics.ChooseCoordinateY();
            Graphics.SetCoordinates(board,coordinateX,scanner.nextInt());
        }
    }

}
