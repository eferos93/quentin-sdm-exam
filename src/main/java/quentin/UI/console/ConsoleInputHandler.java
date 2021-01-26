package quentin.UI.console;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHandler implements quentin.UI.InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int getInteger() throws InputMismatchException {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            throw new InputMismatchException();
        }
    }

    @Override
    public boolean askPie() throws InputMismatchException {
        String answer = scanner.next();
        if (!(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("yes"))) {
            throw new InputMismatchException();
        }
        return answer.equalsIgnoreCase("yes");
    }

    @Override
    public String askBlackPlayerName() {
        return scanner.next();
    }

    @Override
    public String askWhitePlayerName() {
        return scanner.next();
    }
}

