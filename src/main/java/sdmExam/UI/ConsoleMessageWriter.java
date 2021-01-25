package sdmExam.UI;

import sdmExam.core.Player;

public class ConsoleMessageWriter {

    public static void printTitle() {System.out.println(Message.TITLE);}

    public static void printInstructions() {System.out.println(Message.INSTRUCTIONS);}

    public static void blackPlayerPlayFirst() {System.out.println(Message.BLACK_PLAYS_FIRST);}

    public static void askBoardSize() {
        System.out.println(Message.ASK_SIZE);
    }

    public static void askRowCoordinate() {
        System.out.println(Message.CHOOSE_ROW);
    }

    public static void askColumnCoordinate() {
        System.out.println(Message.CHOOSE_COLUMN);
    }

    public static void displayPlayer(Player player) {
        System.out.printf(Message.CURRENT_PLAYER, player.getName(), ConsoleStoneRepresentation.getStoneValue(player.getColor()));
    }

    public static void notifyPass(){
        System.out.println(Message.PASS_TURN);
    }

    public static void notifyPieRule(Player player1, Player player2){
        System.out.printf(Message.PIE, player1.getName(), ConsoleStoneRepresentation.getStoneValue(player1.getColor()),
                player2.getName(), ConsoleStoneRepresentation.getStoneValue(player2.getColor()));
    }

    public static void notifyWinner(Player player) {
        System.out.printf(Message.END_GAME, player.getName());
    }

    public static void askPie() {System.out.println(Message.QUERY_PIE);}
}