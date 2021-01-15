package sdmExam;

import sdmExam.UI.Graphics;
import sdmExam.UI.PrintBoard;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board();

        Graphics.printTitle();
        Graphics.Instructions();
        Graphics.BlackPlayerPlayFirst();
        PrintBoard.Print(board);
        Graphics.Set(Stone.BLACK);
        Graphics.Pie();
        if(scanner.nextInt()==1)
            Graphics.ApplyPie();
        else{Graphics.Set(Stone.WHITE);}
    }

}
