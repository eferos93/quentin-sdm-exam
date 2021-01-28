package quentin.UI;

import quentin.core.Board;
import quentin.core.Player;

import java.util.List;

public interface OutputHandler {
//    void notifyInvalidCoordinateInput();
//
//    void notifyInvalidSizeInput();
//
//    void notifyBlackPlayerPlayFirst();
    void askRowCoordinate();

    void askColumnCoordinate();

    void displayPlayer(Player player);

    void notifyPass(Player currentPlayer);

    void notifyPieRule(List<Player> players);

    void notifyWinner(Player player);

    void askPie();

    void displayBoard(Board board);

    class Message {
        public static final String TITLE =  "QUENTIN GAME";
        public final static String INSTRUCTIONS = """
            The Black player B should connect the black board sides (top and bottom)
            The White player W should connect the white board sides (left and right)
            For more info visit: https://boardgamegeek.com/boardgame/124095/quentin""";
        public static final String BLACK_PLAYS_FIRST = "Black player plays first";
        public static final String CHOOSE_ROW = "Choose row coordinate: ";
        public static final String CHOOSE_COLUMN = "Choose column coordinate: ";
        public static final String QUERY_PIE = "Apply pie rule? yes/no";
        public static final String PIE = """
            Pie rule applied.
            %s's color: %s
            %s's color: %s 
            """;
        public static final String ASK_SIZE = "Choose the dimension of the board between 4 and 13";
        public static final String INVALID_SIZE_INPUT = "Invalid size. Please select a valid input size for the board.";
        public static final String INVALID_COORDINATE_INPUT = "Invalid input coordinate. Please choose a valid intersection inside the board";
        public static final String CURRENT_PLAYER = "%s's turn. %s\n";
        public static final String END_GAME = "Congratulations to %s %s, you won!";
        public static final String PASS_TURN = "%s passes turn as there no possible move to make";
        public static final String ASK_BLACK_PLAYER_NAME = "Please insert the name of the Black Player:";
        public static final String ASK_WHITE_PLAYER_NAME = "Please insert the name of the White Player:";
    }
}
