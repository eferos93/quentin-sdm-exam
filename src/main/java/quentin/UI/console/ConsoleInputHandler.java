package quentin.UI.console;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHandler implements quentin.UI.InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInteger() throws InputMismatchException {
        if (scanner.hasNextInt()) {
            int answer = scanner.nextInt();
            scanner.nextLine();
            return answer;
        } else {
            scanner.nextLine();
            throw new InputMismatchException("You have not inserted a valid integer! Retry.");
        }
    }

    public static boolean wantToReplay() throws InputMismatchException {
        String answer = scanner.next();
        if (!(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("yes"))) {
            throw new InputMismatchException("You should insert 'yes' or 'no'");
        }
        scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }

    @Override
    public boolean askPie(String whitePlayerName) throws InputMismatchException {
        String answer = scanner.next();
        if (!(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("yes"))) {
            throw new InputMismatchException("You should insert 'yes' or 'no'");
        }
        scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }

    public static String askPlayerName() {
        return scanner.nextLine();
    }
}

