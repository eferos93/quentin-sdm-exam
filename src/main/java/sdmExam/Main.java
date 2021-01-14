package sdmExam;

import sdmExam.UI.Graphics;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int x;
        Graphics.printTitle();
        Graphics.Instructions();
        Graphics.BlackPlayerPlayFirst();
        Graphics.ChooseCoordinateX();
        x=scanner.nextInt();
        Graphics.ChooseCoordinateY();
        Graphics.SetCoordinates(x,scanner.nextInt());

    }

}
