package quentin.exceptions;

public class RepeatedPlayException extends Exception {
    public RepeatedPlayException(){
        super("A player cannot play twice in a row.");
    }
}
