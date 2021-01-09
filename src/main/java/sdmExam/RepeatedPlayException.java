package sdmExam;

public class RepeatedPlayException extends Exception{
    public RepeatedPlayException(String player_colour){

        super("A player" + player_colour + " cannot play twice in a row.");
    }
}
