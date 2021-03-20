package quentin.ui.console;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputHandler implements quentin.ui.InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public int getInteger() throws NoSuchElementException {
        if (scanner.hasNextInt()) {
            int answer = scanner.nextInt();
            scanner.nextLine();
            return answer;
        } else {
            scanner.nextLine();
            throw new InputMismatchException("You have not inserted a valid integer! Retry.");
        }
    }

    public boolean wantToReplay() throws InputMismatchException {
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

    public String askPlayerName() {
        return scanner.nextLine();
    }
}

