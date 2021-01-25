package quentin.UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInteger() throws InputMismatchException {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            throw new InputMismatchException();
        }
    }

    public boolean askPie() throws InputMismatchException {
        String answer = scanner.next();
        if (!(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("yes"))) {
            throw new InputMismatchException();
        }
        return answer.equalsIgnoreCase("yes");
    }
}

