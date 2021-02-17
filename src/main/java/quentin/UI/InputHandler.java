package quentin.UI;

import quentin.core.Player;

import java.util.InputMismatchException;

public interface InputHandler {
    boolean askPie(Player player) throws InputMismatchException;
}
