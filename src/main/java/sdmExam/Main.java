package sdmExam;

import sdmExam.UI.Graphics;
import sdmExam.UI.InputHandle;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Board board = new Board();
        Game game= new Game();
        Graphics.printTitle();
        Graphics.Instructions();
        InputHandle inputHandler = new InputHandle();
        int size = inputHandler.askSize();
        /*while (Graphics.SomeoneWin()) {
            Graphics.Set(Stone.BLACK, board, game);
            Graphics.Set(Stone.WHITE, board, game);
        }*/

    }

}
