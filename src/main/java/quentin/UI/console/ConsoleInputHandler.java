package quentin.UI.console;

import quentin.core.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHandler implements quentin.UI.InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInteger() throws InputMismatchException {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.next();
            throw new InputMismatchException("You have not inserted a valid integer! Retry.");
        }
    }

    @Override
    public boolean askPie(Player player) throws InputMismatchException {
        String answer = scanner.next();
        if (!(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("yes"))) {
            throw new InputMismatchException("You should insert 'yes' or 'no'");
        }
        return answer.equalsIgnoreCase("yes");
    }

    public static String askBlackPlayerName() {
        return scanner.next();
    }
    public static String askWhitePlayerName() {
        return scanner.next();
    }
}

