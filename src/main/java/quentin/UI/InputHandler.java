package quentin.UI;

import quentin.UI.console.ConsoleInputHandler;

import java.util.InputMismatchException;

public interface InputHandler {
    int getInteger() throws InputMismatchException;
    boolean askPie() throws InputMismatchException;
}
