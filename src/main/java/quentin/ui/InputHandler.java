package quentin.ui;
import java.util.InputMismatchException;

public interface InputHandler {
    boolean askPie(String whitePlayerName) throws InputMismatchException;
}
