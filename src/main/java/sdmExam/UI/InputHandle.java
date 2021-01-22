package sdmExam.UI;

import sdmExam.UI.Exception.OutOfBoardException;
import sdmExam.UI.Exception.PieException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandle   {


    private static final Scanner scanner = new Scanner(System.in);

    public int askSize() {
        System.out.println(Message.ASK_SIZE);
        int size;
        try {
            size = getInteger();
            if (size < 3 || size > 11)
                throw new OutOfBoardException(Message.INVALID_INPUT_SIZE);

        } catch (OutOfBoardException error) {
            System.out.println(error.getMessage());
            return askSize();
        }
        return size;
    }

    private static int getInteger() {
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
            if(!(answer.toLowerCase().equals("n") || answer.toLowerCase().equals("y")))
                throw new PieException(Message.INVALID_INPUT_SIZE);
        } catch(PieException error) {
            System.out.println(error.getMessage());
            scanner.next();
            return askPie();
        }
        return answer.toLowerCase().equals("y");
    }



}

