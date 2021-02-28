package quentin.exceptions;

public class RepeatedPlayException extends QuentinException {
    public RepeatedPlayException(){
        super("A player cannot play twice in a row.");
    }
}
