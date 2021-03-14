package quentin.exceptions;

public class InvalidFirstPlayerException extends QuentinException {

    public InvalidFirstPlayerException(){
        super("Black player should play first");
    }
}
