package sdmExam.UI;

import sdmExam.Player;

public class ConsoleMessageWriter {

    public static void printTitle() {System.out.println(Message.TITLE);}

    public static void printInstructions() {System.out.println(Message.INSTRUCTIONS);}

    public static void blackPlayerPlayFirst() {System.out.println(Message.BLACK_PLAYS_FIRST);}

    public static void displayPlayer(Player player) {
        System.out.printf(Message.CURRENT_PLAYER, player.getName(), ConsoleStoneRepresentation.getStoneValue(player.getColor()));
    }

    public static void askPie() {System.out.println(Message.PIE);}
}