package sdmExam.UI;

import sdmExam.UI.Exception.PieException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandle   {


    private static final Scanner scanner = new Scanner(System.in);


    public static int getInteger() {
        try {
            if (scanner.hasNextInt())
                return scanner.nextInt();
            else
                throw new InputMismatchException();
        }
        catch (InputMismatchException error) {
            System.out.println(Message.INVALID_NUMBER_INPUT);
            scanner.next();
            return getInteger();
        }
    }

    public boolean askPie() {
        Graphics.Pie();
        String answer;
        try {
            answer = scanner.next();
            if(!(answer.toLowerCase().equals("no") || answer.toLowerCase().equals("yes")))
                throw new PieException(Message.INVALID_INPUT_SIZE);
        } catch(PieException error) {
            System.out.println(error.getMessage());
            scanner.next();
            return askPie();
        }
        return answer.toLowerCase().equals("yes");
    }



}

