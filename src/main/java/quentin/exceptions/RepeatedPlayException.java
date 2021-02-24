package quentin.exceptions;

public class RepeatedPlayException extends quentinException {
    public RepeatedPlayException(){
        super("A player cannot play twice in a row.");
    }
}
