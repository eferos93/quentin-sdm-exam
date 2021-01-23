package sdmExam;

import sdmExam.UI.Graphics;
import sdmExam.UI.PrintBoard;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Board board = new Board();
        Game game= new Game();
        PrintBoard.Print(board);
        Graphics.printTitle();
        Graphics.Instructions();
        Graphics.BlackPlayerPlayFirst();
        Graphics.Set(Stone.BLACK,board, game);
        Graphics.Pie();
        if(scanner.nextInt()==1)
            Graphics.ApplyPie(board);
        else{Graphics.Set(Stone.WHITE,board,game);}
    }

}
