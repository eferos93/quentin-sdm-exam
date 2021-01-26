package quentin.UI;

import java.util.InputMismatchException;

public interface InputHandler {
    int getInteger() throws InputMismatchException;
    boolean askPie() throws InputMismatchException;
}
