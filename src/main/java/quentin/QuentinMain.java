package quentin;


import quentin.UI.console.ConsoleInputHandler;
import quentin.UI.console.ConsoleOutputHandler;
import quentin.core.Quentin;

public class QuentinMain {

    public static void main(String... args) {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> quentin =
                new Quentin<>(5, new ConsoleInputHandler(), new ConsoleOutputHandler());

        quentin.run();
    }
}
