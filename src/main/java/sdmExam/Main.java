package sdmExam;

import sdmExam.UI.Graphics;
import sdmExam.UI.InputHandle;
import sdmExam.UI.PrintBoard;

public class Main {

    public static void main(String[] args) throws Exception {
        Board board = new Board();
        Game game= new Game();
        InputHandle inputHandle= new InputHandle();

        Graphics.printTitle();
        Graphics.Instructions();
        Graphics.BlackPlayerPlayFirst();
        PrintBoard.Print(board);
        Graphics.Set(Stone.BLACK,board, game);
        inputHandle.askPie();
        if(inputHandle.getInteger()==1)
            Graphics.ApplyPie(board);
        else{Graphics.Set(Stone.WHITE,board,game);}
    }

}
