package sdmExam.UI;

import sdmExam.exceptions.PieException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHandler {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getInteger() {
        try {
            if (scanner.hasNextInt())
                return scanner.nextInt();
            else
                throw new InputMismatchException();
        }
        catch (InputMismatchException error) {
            System.out.println(Message.INVALID_COORDINATE_INPUT);
            scanner.next();
            return getInteger();
        }
    }

    public boolean askPie() {
        ConsoleOutputHandler.askPie();
        String answer;
        try {
            answer = scanner.next();
            if(!(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("yes")))
                throw new PieException(Message.INVALID_SIZE_INPUT);
        } catch(PieException error) {
            System.out.println(error.getMessage());
            scanner.next();
            return askPie();
        }
        return answer.equalsIgnoreCase("yes");
    }
}

