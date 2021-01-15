package sdmExam;

import sdmExam.UI.Graphics;
import sdmExam.UI.PrintBoard;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int x;
        Game game = new Game();
        Board board = new Board();

        Graphics.printTitle();
        Graphics.Instructions();
        Graphics.BlackPlayerPlayFirst();
        PrintBoard.PrintBoard(board);
        Graphics.ChooseCoordinateX();
        x=scanner.nextInt();
        Graphics.ChooseCoordinateY();
        Graphics.SetCoordinates(board,x,scanner.nextInt());

    }

}
